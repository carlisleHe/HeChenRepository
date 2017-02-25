#!/bin/sh

#function:UpdateRes ������Դ��
#params:
# RES_PATH:��Դ��ַ
#	TAR_NAME:��������
UpdateRes()
{
	echo "======����Apache��Դ����ʼ��======"
	echo "��Դ�ļ�Ŀ¼:[$RES_PATH]"
  echo "��Դ������:[$TAR_NAME]"
	
	echo "\n"
	
	DEPLOY_PATH=$APA_PATH/$TAR_NAME
	echo "��װ��·����"$DEPLOY_PATH
	BAKTAR_PATH=$BAK_PATH/$TAR_NAME.`date +%Y%m%d`
	echo "���ݰ�·����"$BAKTAR_PATH
	NEWTAR_PATH=$NEW_PATH/$TAR_NAME
	echo "�°�·����"$NEWTAR_PATH
	echo "\n"
	echo "�����°�......"
	echo "\n"
	echo "�����°���С"
	ls -l $NEWTAR_PATH
	
	echo "ȷ���·�����С������밴enter������...\c"
	read option
	echo "\n"
	
	echo "�Ƿ񱸷�������Դ�ļ���(y����|n������)\c"
	read option
	case "$option" in 
		y|Y)
			echo "����������Դ�ļ�......"
			echo "\n"
			echo "��ʼ�����Դ�ļ�������ִ�У�tar cvf $BAKTAR_PATH $RES_PATH......"
			tar cvf $BAKTAR_PATH $RES_PATH
	
			echo "\n"
			echo "��Դ�ļ���������ѳɹ�"
			ls -l $BAKTAR_PATH
			echo "\n"
			;;
		*)
			;;
	esac
	
	echo "\n"
	echo "��ʼ������from $NEWTAR_PATH to $DEPLOY_PATH ......"
	cp $NEWTAR_PATH $DEPLOY_PATH
	
	echo "\n"
	echo "��˶��°�װ����С�Ƿ�һ��"
	ls -l $NEWTAR_PATH
	ls -l $DEPLOY_PATH
	
	echo "��enter������...\c"
	read option
	echo "\n"
	
	echo "��ʼ����Apache��̬��Դ�ļ�......"
	cd $APA_PATH
	tar xvf $TAR_NAME
	
}


echo "����Apache��Դ�ļ���ʼ......"

echo "\n"

###ȫ������###
echo "======������ȫ������======"

###�°�Ŀ¼###
NEW_PATH="/apache/filenew"
echo "�°�Ŀ¼:[$NEW_PATH]"

###����Ŀ¼###
BAK_PATH="/apache/filebak"
echo "����Ŀ¼:[$BAK_PATH]"

###Apache��Ŀ¼###
APA_PATH="/apache/dist"
echo "Apache��Ŀ¼:[$APA_PATH]"

flag="true"
update_option="A"

while [ "$flag" = "true" ]
do
	echo "\nA-ȫ�� ;\nB-΢������;\nC-���Ż���;\nD-֧��������;\n\n��ѡ��Ҫ������Դ��Ӧ�ã�\a"	
	read update_option
	case "$update_option" in
		a|A)
			echo "\n......\n"
			echo "������ȫ��Ӧ����Դ��"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "������΢������Ӧ����Դ��"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "�����¿��Ż���Ӧ����Դ��"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "������֧��������Ӧ����Դ��"
			flag="false"
			;;
		*)
			echo "\n......\n"
			echo "�����ѡ�����������...\n"
			continue
			;;
	esac
done

echo "\n"

echo "������Ϣ��ȷ��?(y����/n�ж�):\c"
read option

case "$option" in 
	y|Y)
	
		;;
	*)
	echo "\n"
	echo "���ò������ж��˳�����ȷ��������ú�����ִ�У�"
	exit 0
		;;
	esac

echo "\n......\n"

echo "\n...���¿�ʼ...\n"

case "$update_option" in
	a|A)
		RES_PATH=$APA_PATH/openbank/weibank
		TAR_NAME="weibank-resources.tar"
		UpdateRes
		RES_PATH=$APA_PATH/openbank/openbank
		TAR_NAME="openbank-resources.tar"
		UpdateRes
		RES_PATH=$APA_PATH/openbank/alipaybank
		TAR_NAME="alipaybank-resources.tar"
		UpdateRes
		;;
	b|B)
		RES_PATH=$APA_PATH/openbank/weibank
		TAR_NAME="weibank-resources.tar"
		UpdateRes
		;;
	c|C)
		RES_PATH=$APA_PATH/openbank/openbank
		TAR_NAME="openbank-resources.tar"
		UpdateRes
		;;
	d|D)
		RES_PATH=$APA_PATH/openbank/alipaybank
		TAR_NAME="alipaybank-resources.tar"
		UpdateRes
		;;
esac

echo "======����Apache��Դ�ļ��ű�ִ�н�����======"	
echo "\n"