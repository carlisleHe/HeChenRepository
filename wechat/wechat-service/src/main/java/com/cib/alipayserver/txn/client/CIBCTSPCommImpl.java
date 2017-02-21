package com.cib.alipayserver.txn.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TuxedoClient.MessageProcess.MessageProcessService;
import TuxedoClient.exception.DomainException;

import com.intensoft.coresyst.CIBTxnComm;
import com.intensoft.coresyst.CIBTxnMsg;
import com.intensoft.coresyst.exception.AppExcCode;
import com.intensoft.coresyst.exception.CoreSystException;

/**
 * CTSP 直连核心
 * @author wubh
 * @description
 * @since 2012-12-12
 */
public class CIBCTSPCommImpl implements CIBTxnComm {

	private static final Logger LOG_CTSP = LoggerFactory.getLogger(CIBCTSPCommImpl.class);
	private static final String FIX_TUX_HEAD = "tuxedo:";
	private static final int FILE_MAC_LENGTH = 8;
	private static Long serial = 0L;

	@Override
	public CIBTxnComm newInstance() {
		return new CIBCTSPCommImpl();
	}

    public CIBCTSPCommImpl(){
    }
    
    @Override
    public void doRR(CIBTxnMsg rrStream) throws CoreSystException {

        try {
			String serial = genSerial();
			doLog(rrStream, true, serial);

        	//使用此服务前，需有环境的支持。包括安装TCP或JNI加密程序，将配置文件放至用户主目录等
        	MessageProcessService mp = new MessageProcessService();
        	
            int offset = getEncryptOffSet(rrStream.getData());
            byte[] sendbyte = new byte[rrStream.getLength() - offset];
            System.arraycopy(rrStream.getData(), offset, sendbyte, 0, sendbyte.length);
            byte[] recebyte = mp.ctsptransfer(sendbyte);

			System.arraycopy(recebyte, 0, rrStream.getData(), 0, recebyte.length);
			rrStream.setLength(recebyte.length);
			doLog(rrStream, false, serial);
		} catch (DomainException e1) {
			throw new CoreSystException(AppExcCode.CORESYST_DEFAULT_ERR, e1.getErrCode() + e1.getMessage(), e1);
		} catch (Exception e) {
			LOG_CTSP.error("ctsp通讯失败", e);
            throw new CoreSystException(AppExcCode.CORESYST_UNKNOWN_ERR, e.getMessage());
        }

    }

	/**
	 * 生成序列号（记录报文时使用）
	 * 
	 * @return
	 */
	public synchronized String genSerial() {
		if (serial == null) {
			serial = 0L;
		} else {
			serial++;
		}
		return String.format("%010d", serial);
	}

	/**
	 * 记录CTSP报文日志
	 * 
	 * @param rrStream
	 * @param isRequest
	 * @throws UnsupportedEncodingException
	 */
	private void doLog(CIBTxnMsg rrStream, boolean isRequest, String serial)
			throws UnsupportedEncodingException {
		StringBuilder sbd = new StringBuilder(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss z").format(new Date()));
		sbd.append(isRequest ? " : out-req = [" : " :  out-resp = [");
		sbd.append(serial).append("][");
		sbd.append(new String(rrStream.getData(), 0, rrStream.getLength(),
				"GBK"));
		sbd.append("]");
		LOG_CTSP.info(sbd.toString());
	}

    /**
     * 获取数据中加密协议报文的偏移量
     * @param data
     * @return
     */
    private int getEncryptOffSet(byte[] data) {
        //data中的内容为 1字节的加密元素数量 加密元素数量*4字节的加密协议报文头 剩余为交易报文内容
        int encryptCount = data[0] - 0x30;//加密元素数量
        int encryptHeadBytesLen = encryptCount * 4;//加密协议报文头长度
        return encryptHeadBytesLen + 1;
    }

    @Override
    public void writeSocket(Socket aSocket, byte[] data, int offset, int size) throws IOException {
    	OutputStream out = aSocket.getOutputStream();
		int l = size + 2;
		byte[] buffer = new byte[2];
		buffer[0] = (byte) ((l >> 8) & 0x0ff);
		buffer[1] = (byte) ((l) & 0x0ff);
		out.write(buffer, offset, buffer.length);
		out.write(data, offset, size);
		out.flush();
		StringBuilder sbd = new StringBuilder(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss z").format(new Date()));
		sbd.append(": in-resp:[");
		sbd.append(new String(data, 0, size, "GBK"));
		sbd.append("]");
		LOG_CTSP.info(sbd.toString());
    }

    @Override
    public void writeSocket(Socket aSocket, byte[] data) throws IOException {
    	writeSocket(aSocket, data, 0, data.length);
    }

    @Override
    public byte[] readSocket(Socket aSocket) throws CoreSystException, IOException {
    	byte[] buffer = new byte[2];
		InputStream sock_in = aSocket.getInputStream();
		sock_in.read(buffer, 0, 2);
		int l = 0;
		l |= (buffer[1] & 0x0ff);
		l |= (buffer[0] & 0x0ff) << 8;
		if (l <=0 ) {
			throw new CoreSystException(AppExcCode.CORESYST_DATA_RECEIVE_ERR,
					"Received txn data is null.");
		}
		if (l > 8192) {
			throw new CoreSystException(AppExcCode.CORESYST_DATA_RECEIVE_ERR,
			"Received txn data exceed the buffer.");
		}
		l = l - 2;
		int off = 0;
		buffer = new byte[l];

		while (off < l) {
			int i = sock_in.read(buffer, off, l - off);

			if (i < 0) {
				throw new CoreSystException(AppExcCode.CORESYST_SOCKET_ERR,
				"The socket meet unexpected close.");
			}

			off += i;
		}
		StringBuilder sbd = new StringBuilder(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss z").format(new Date()));
		sbd.append(": in-req:[");
		sbd.append(new String(buffer, 0, l, "GBK"));
		sbd.append("]");
		LOG_CTSP.info(sbd.toString());
		return buffer;
    }

    @Override
    public String putFile(String srcFileName, String tgtFileName) throws CoreSystException {
    	CTSPFileService fileService = new CTSPFileService();
    	return fileService.uploadFile(srcFileName);
    }

    @Override
    public void getFile(String srcFileName, String tgtFileName) throws CoreSystException {
    	if(srcFileName!=null && srcFileName.startsWith("tuxedo")){
    		String fileMac=srcFileName.substring(FIX_TUX_HEAD.length(), FIX_TUX_HEAD.length()+FILE_MAC_LENGTH);
    		String fileName=srcFileName.substring(FIX_TUX_HEAD.length()+FILE_MAC_LENGTH);
    		CTSPFileService fileService = new CTSPFileService();
        	fileService.downloadFile(fileName, fileMac, tgtFileName);
    	}else{
    		CTSPFileService fileService = new CTSPFileService();
        	fileService.getLocalFile(srcFileName,tgtFileName);
    	}
    }

    @Override
    public void setTxnProxyHost(String txnProxyHost) {
    }

    @Override
    public void setTxnProxyPort(int txnProxyPort) {
    }

    @Override
    public void setTxnTimeout(int txnTimeout) {
    }
}

