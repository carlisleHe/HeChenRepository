#!/bin/sh

#0-RUNNING,1-SHUTDOWN,2-OTHERS
SrvrState=0
#function:GetSrvrState ��ȡ��ǰ��Ӧweblogic������״̬ 
#params:
#	SRVRNAME:��Ӧ��������
#	ADMIN_URL:��Ӧ����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
# return SrvrState,����0-RUNNING,1-SHUTDOWN,2-OTHERS
GetSrvrState(){
	java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" GETSTATE "$SRVRNAME"|read tmp
	RSLT=$?
	if [ RSLT -ne 0 ]
	then
		echo "�޷���ȷ���ӷ�����������ȡ������״̬�������˳���"
		echo "\n"
		exit 1
	else
		echo "������������Ϣ��"$tmp
		echo "\n"
		echo $tmp |awk '{print $6}'|read tmp
		case $tmp in
			SHUTDOWN)
				echo "��������ǰ״̬Ϊ�ر�[$tmp]��"
				echo "\n"
				SrvrState=1
				;;
			RUNNING)
				echo "��������ǰ״̬Ϊ������[$tmp]��"
				echo "\n"
				SrvrState=0
				;;
			*)
				echo "��������ǰ״̬Ϊδ֪[$tmp]��"
				echo "\n"
				SrvrState=2
				;;
		esac
	fi
}

#function:Start��������
#params:
#	SRVRNAME:��������
#	ADMIN_URL:����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
Start()
{		
	flag="true"

	echo "ȷ��$SRVRNAME�����Ѿ��ر�......"
	echo "\n"
	
	flag="true"
	while [ "$flag" = "true" ]
	do
		GetSrvrState
		if [ $SrvrState != 1 ]
		then
			##��������״̬Ϊδ֪����SHUTDOWN,RUNNING��
			if [ $SrvrState = 2 ]
			then
				echo "δ֪�ķ�����״̬��ǿ��ִ�йر�ָ�"
				echo "��ʼǿ�ƹرշ�����......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
				RSLT=$?
				if [ RSLT -ne 0 ]
				then
					echo "ֹͣ���������������˳���"
					echo "\n"
					exit 1
				fi
			##��������״̬Ϊ0(������)
			else
				echo "��ʼ�����رշ�����......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 10  SHUTDOWN "$SRVRNAME"		
				RSLT=$?
				if [ RSLT -ne 0 ]
				then
					echo "ֹͣ���������������˳���"
					echo "\n"
					exit 1
				fi
			fi		
		else
			flag="false"
		fi
	done
	
	echo "��ʼ����weblogic����......"
	SRVRPATH=$DOMAIN_PATH/servers/$SRVRNAME
	echo "������Ŀ¼Ϊ:"$SRVRPATH
	echo "\n"
	echo "���������Ŀ¼��ɾ��[$SRVRPATH/tmp],[$SRVRPATH/stage]"
	if [ -d "$SRVRPATH/tmp" ]; then 
	  rm -r $SRVRPATH/tmp > /dev/null
	fi
	if [ -d "$SRVRPATH/stage" ]; then 
		rm -r $SRVRPATH/stage > /dev/null
  fi
  
  echo "��ʼ����[$SRVRNAME]����......"
	
	java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  START "$SRVRNAME"		
	RSLT=$?
	if [ RSLT -ne 0 ]
	then
		echo "����������������ȷ�����ָ��"
		echo "\n"
		exit 1
	else
		GetSrvrState
		if [ $SrvrState != 0 ]
		then 
			echo "�޷����������������������˳���"
			echo "\n"
			exit 1
		fi
	fi
	
	echo "\n"
  echo "======����[$SRVRNAME]�ɹ���======"
	echo "\n"
}	

#function:StartAdmin����AdminServer
#params:
#	SRVRNAME:��������
#	ADMIN_URL:����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
StartAdmin()
{		
  cd $DOMAIN_PATH
  nohup sh ./startWebLogic.sh >>nohup.out 2>&1 &
  aflag="true"
  sleep 20
  while [ "$aflag" = "true" ]
	do
	  #��AdminServer�Ƿ������ɹ�
	  sleep 10
	  GetSrvrState
	  if [ $SrvrState == 0 ]
		then
		  aflag="false"
		else
		  echo "���AdminServer״̬ʧ�ܣ�10�������..."
		fi
	done
	echo "\n"
  echo "======����[$SRVRNAME]�ɹ���======"
	echo "\n"
}	



echo "��������ű���ʼ......"

echo "\n"

###ȫ������###
echo "======������ȫ������======"

###����������Ŀ¼###
DEPLOY_PATH=/web/webapps/war
echo "����������Ŀ¼:[$DEPLOY_PATH]"

###�������̨��ַ###
ADMIN_URL=t3://localhost:7001
echo "�������̨��ַ:[$ADMIN_URL]" 

###weblogicӦ�÷����·��###
DOMAIN_PATH=/web/domains/openbank
echo "weblogicӦ�÷����·��:[$DOMAIN_PATH]"

###weblogic.jar·��###
WLS_JAR_PATH=/web/Oracle/Middleware/wlserver_10.3/server/lib/weblogic.jar

echo "\n"

###΢�����з�������###
WEIBANK_SRVRNAME=weibank
echo "΢�����з�����:[$WEIBANK_SRVRNAME]"

###���Ż�����������###
OPENBANK_SRVRNAME=openbank-web
echo "���Ż���������:[$OPENBANK_SRVRNAME]"

###֪ͨ��������###
NOTIFY_SRVRNAME=openbank-notify
echo "֪ͨ������:[$NOTIFY_SRVRNAME]"

###��̨��������###
MNGR_SRVRNAME=openbank-manager
echo "��̨������:[$MNGR_SRVRNAME]"

###AdminServer����###
ADMIN_SRVRNAME=AdminServer
echo "AdminServer������:[$ADMIN_SRVRNAME]"

###֧�������񴰷�������###
ALIPAYBANK_SRVRNAME=alipaybank
echo "֧�������񴰷�����:[$ALIPAYBANK_SRVRNAME]"

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

flag="true"
deploy_option="A"

while [ "$flag" = "true" ]
do
	echo "\nA-ȫ��Ӧ�ã�������̨��;\nB-��ҪӦ�ã���������̨��;\nC-΢�����з���;\nD-���Ż�������;\nE-֪ͨ����;\nU-֧�������񴰷���;\nF-��̨����; \nS-AdminServer�������\nP-��ҪӦ��(��������̨)��AdminServer��\nT-����Ӧ�ú�AdminServer��\n��ѡ��Ҫ������Ӧ�ã�\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "������ȫ��Ӧ�÷��񣨰�����̨����"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "��������Ҫ���񣨲�������̨����"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "������΢�����з���"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "���������Ż�������"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "������֪ͨ����"
			flag="false"
			;;
	  f|F)
			echo "\n......\n"
			echo "��������̨����"
			flag="false"
			;;
		s|S)
			echo "\n......\n"
			echo "������AdminServer(�������)��"
			flag="false"
			;;
		p|P)
			echo "\n......\n"
			echo "��������Ҫ����(��������̨)��AdminServer��"
			flag="false"
			;;
		t|T)
			echo "\n......\n"
			echo "���������з���(������̨)��AdminServer��"
			flag="false"
			;;
		u|U)
			echo "\n......\n"
			echo "������֧�������񴰷���"
			flag="false"
			;;
		*)
			echo "\n......\n"
			echo "�����ѡ�����������...\n"
			continue
			;;
	esac
done

echo "\n......\n"

echo "Weblogic����̨�û���:weblogic"
WLS_USER="weblogic"
echo "\n"

while [ -z "$WLS_PASSWD" ]
do
	stty -echo
	echo $WLS_USER"�û�����:\c"
	read WLS_PASSWD
	stty echo
	if [ -z "$WLS_PASSWD" ] 
	then
		echo "����̨���벻��Ϊ��, ����������!" 
	fi
	echo "\n"
done

echo "\n...��ʼ��������...\n"

case "$deploy_option" in
	a|A)
		SRVRNAME=$WEIBANK_SRVRNAME
		Start
		SRVRNAME=$OPENBANK_SRVRNAME
		Start
		SRVRNAME=$NOTIFY_SRVRNAME
		Start
		SRVRNAME=$MNGR_SRVRNAME
		Start
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Start
		;;
		
	b|B)
		SRVRNAME=$WEIBANK_SRVRNAME
		Start
        SRVRNAME=$OPENBANK_SRVRNAME
		Start
		SRVRNAME=$NOTIFY_SRVRNAME
		Start
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Start
		;;

	c|C)
		SRVRNAME=$WEIBANK_SRVRNAME
		Start
		;;
		
	d|D)
		SRVRNAME=$OPENBANK_SRVRNAME
		Start
		;;
		
	e|E)
		SRVRNAME=$NOTIFY_SRVRNAME
		Start
		;;
		
	f|F)
		SRVRNAME=$MNGR_SRVRNAME
		Start
		;;

	s|S)
		SRVRNAME=$ADMIN_SRVRNAME
		StartAdmin
		;;
	u|U)
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Start
		;;
		
	p|P)
	    echo "\n...��ʼ����AdminServer..."
		SRVRNAME=$ADMIN_SRVRNAME
		StartAdmin
		echo "\n...��ʼ�����ܹܷ���..."
		SRVRNAME=$WEIBANK_SRVRNAME
		Start
		SRVRNAME=$OPENBANK_SRVRNAME
		Start		
		SRVRNAME=$NOTIFY_SRVRNAME
		Start
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Start
		;;
		
	t|T)
	  echo "\n...��ʼ����AdminServer..."
		SRVRNAME=$ADMIN_SRVRNAME
		StartAdmin
		echo "\n...��ʼ�����ܹܷ���..."
		SRVRNAME=$WEIBANK_SRVRNAME
		Start
		SRVRNAME=$OPENBANK_SRVRNAME
		Start
		SRVRNAME=$NOTIFY_SRVRNAME
		Start
		SRVRNAME=$MNGR_SRVRNAME
		Start
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Start
		;;	
esac


echo "====== �����ű�ִ����� ======"
