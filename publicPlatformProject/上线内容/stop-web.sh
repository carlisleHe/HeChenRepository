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

#function:Stop�رշ���
#params:
#	SRVRNAME:��������
#	ADMIN_URL:����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
Stop()
{		
	echo "ֹͣ��ȷ���Ѿ��ر�$SRVRNAME����......"
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
				echo "δ֪�ķ�����״̬���˳���ǿ��ִ�йر�ָ�(yǿ��ִ��|n�˳�)\c"
				read option
				case "$option" in 
					y|Y)
						echo "��ʼǿ�ƹرշ�����......"
						java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
						RSLT=$?
						if [ RSLT -ne 0 ]
						then
							echo "ֹͣ���������������˳���"
							echo "\n"
							exit 1
						fi
						;;
					*)
						echo "\n"
						echo "�����˳�ָ������˳���"
						echo "\n"
						exit 1
						;;
				esac
			##��������״̬Ϊ0(������)
			else
				echo "��ʼ�����رշ�����......"
	      echo "��ʹ�� ${SHUTDOWN_CMD} �رշ��������ĵȴ�......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 10  $SHUTDOWN_CMD "$SRVRNAME"		
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
	echo "\n"
  echo "======�ر�[$SRVRNAME]�ɹ���======"
	echo "\n"
}	

#function:StopAdmin�ر�AdminServer
#params:
#	SRVRNAME:��������
#	ADMIN_URL:����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
StopAdmin()
{		
	echo "ֹͣ��ȷ���Ѿ��ر�$SRVRNAME����......"
	echo "\n"
	
  GetSrvrState
	if [ $SrvrState != 1 ]
	then
		##��������״̬Ϊδ֪����SHUTDOWN,RUNNING��
		if [ $SrvrState = 2 ]
		then
			echo "δ֪�ķ�����״̬���˳���ǿ��ִ�йر�ָ�(yǿ��ִ��|n�˳�)\c"
			read option
			case "$option" in 
				y|Y)
					echo "��ʼǿ�ƹرշ�����......"
					java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
					RSLT=$?
					if [ RSLT -ne 0 ]
					then
						echo "ֹͣ���������������˳���"
						echo "\n"
						exit 1
					fi
					;;
				*)
					echo "\n"
					echo "�����˳�ָ������˳���"
					echo "\n"
					exit 1
					;;
			esac
		##��������״̬Ϊ0(������)
		else
			echo "��ʼ�����رշ�����......"
      echo "��ʹ�� ${SHUTDOWN_CMD} �رշ��������ĵȴ�......"
			java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 300  $SHUTDOWN_CMD "$SRVRNAME"		
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
	
	echo "\n"
  echo "======�ر�[$SRVRNAME]�ɹ���======"
	echo "\n"
}	



echo "�رշ���ű���ʼ......"

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

###֪ͨ��������###
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

###AdminServer����###
ALIPAYBANK_SRVRNAME=alipaybank
echo "֧����Ǯ�����񴰷�����:[$ALIPAYBANK_SRVRNAME]"

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
	echo "\nA-ȫ��Ӧ�ã�������̨��;\nB-��ҪӦ�ã���������̨��;\nC-΢�����з���;\nD-���Ż�������;\nE-֪ͨ����;\nU-֧����Ǯ������;\nF-��̨����; \nS-AdminServer�������\nP-��Ҫ����(��������̨)��AdminServer��\nT-���з����AdminServer��\n\n��ѡ��Ҫ�رյ�Ӧ�ã�\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "���ر�ȫ��Ӧ�÷��񣨰�����̨����"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "���ر���ҪӦ�÷��񣨲�������̨����"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "���ر�΢�����з���"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "���رտ��Ż�������"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "���ر�֪ͨ����"
			flag="false"
			;;
		f|F)
			echo "\n......\n"
			echo "���رպ�̨����"
			flag="false"
			;;
		s|S)
			echo "\n......\n"
			echo "���ر�AdminServer�������"
			flag="false"
			;;		
		p|P)
			echo "\n......\n"
			echo "���ر���Ҫ����(��������̨)��AdminServer��"
			flag="false"
			;;	
		t|T)
			echo "\n......\n"
			echo "���ر����з���(������̨)��AdminServer��"
			flag="false"
			;;
		u|U)
			echo "\n......\n"
			echo "���ر�֧����Ǯ�����񴰣�"
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
echo "�Ƿ�ʹ��"FORCESHUTDOWN"ǿ�ƹرշ���?(y��/n��):\c"
read force

case "$force" in 
	y|Y)
		SHUTDOWN_CMD="FORCESHUTDOWN"
		;;
	n|N)
		SHUTDOWN_CMD="SHUTDOWN"
		;;
	*)
		echo "\n"
		echo "���ò������ж��˳�����ȷ�Ϻ�����ִ�У�"
		exit 0
		;;
esac

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

echo "\n...��ʼ�رշ���...\n"

case "$deploy_option" in
	a|A)
		SRVRNAME=$WEIBANK_SRVRNAME
		Stop		
		SRVRNAME=$OPENBANK_SRVRNAME
		Stop
		SRVRNAME=$NOTIFY_SRVRNAME
		Stop
		SRVRNAME=$MNGR_SRVRNAME
		Stop
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Stop
		;;

	b|B)
		SRVRNAME=$WEIBANK_SRVRNAME
		Stop
		SRVRNAME=$OPENBANK_SRVRNAME
		Stop
		SRVRNAME=$NOTIFY_SRVRNAME
		Stop
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Stop
		;;

	c|C)
		SRVRNAME=$WEIBANK_SRVRNAME
		Stop
		;;
		
	d|D)
		SRVRNAME=$OPENBANK_SRVRNAME
		Stop
		;;
				
	e|E)
		SRVRNAME=$NOTIFY_SRVRNAME
		Stop
		;;
		
	f|F)
		SRVRNAME=$MNGR_SRVRNAME
		Stop
		;;
		
	s|S)
		SRVRNAME=$ADMIN_SRVRNAME
		StopAdmin
		;;
	u|U)
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Stop
		;;
		
	p|P)
		SRVRNAME=$WEIBANK_SRVRNAME
		Stop
		SRVRNAME=$OPENBANK_SRVRNAME
		Stop		
		SRVRNAME=$NOTIFY_SRVRNAME
		Stop
		SRVRNAME=$ADMIN_SRVRNAME
		StopAdmin
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Stop
		;;			
		
	t|T)
		SRVRNAME=$WEIBANK_SRVRNAME
		Stop
		SRVRNAME=$OPENBANK_SRVRNAME
		Stop
		SRVRNAME=$NOTIFY_SRVRNAME
		Stop
		SRVRNAME=$MNGR_SRVRNAME
		Stop
		SRVRNAME=$ADMIN_SRVRNAME
		StopAdmin
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		Stop
		;;	
				
esac
