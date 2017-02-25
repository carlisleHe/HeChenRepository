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

#function:UpdateWar ���²����
#params:
#	SRVRNAME:��������
#	WARNAME:���������war������
#	BAK_PATH:����·��
#	NEW_PATH:�°�·��
#	DEPLOY_PATH:����·��
UpdateWar()
{
	echo "======���²������ʼ����������$SRVRNAME,WAR������$WARNAME======"
	echo "\n"
	
	DEPLOYWAR_PATH=$DEPLOY_PATH/$WARNAME
	echo "��װ��·����"$DEPLOYWAR_PATH
	BAKWAR_PATH=$BAK_PATH/$WARNAME.`date +%Y%m%d`
	echo "���ݰ�·����"$BAKWAR_PATH
	NEWWAR_PATH=$NEW_PATH/$WARNAME
	echo "�°�·����"$NEWWAR_PATH
	echo "\n"

	echo "�����°�......"
	echo "\n"
	echo "�����°���С"
	ls -l $NEWWAR_PATH
	
	echo "��enter������...\c"
	read option
	echo "\n"
	
	
	echo "�Ƿ���а����֣�(y����|n������)\c"
	read option
	case "$option" in 
		y|Y)
			echo "��������Ӧ��......"
			echo "\n"
			echo "��ʼ������from $DEPLOYWAR_PATH to $BAKWAR_PATH ....."
			cp $DEPLOYWAR_PATH $BAKWAR_PATH
			echo "\n"
			;;
		*)
			;;
	esac
	
	echo "\n"
	echo "��ʼ������from $NEWWAR_PATH to $DEPLOYWAR_PATH ......"
	cp $NEWWAR_PATH $DEPLOYWAR_PATH
	
	echo "\n"
	echo "��˶��°�װ����С"
	ls -l $NEWWAR_PATH
	ls -l $DEPLOYWAR_PATH
	
	echo "��enter������...\c"
	read option
	echo "\n"
	
	echo "======���²����[$SRVRNAME,$WARNAME]�ɹ���======"
	echo "\n"
}

#function:Deploy ���ݲ����²����°�
#params:
#	SRVRNAME:��������
#	WARNAME:���������war������
#	ADMIN_URL:����URL
#	WLS_USER:weblogic����̨�û�
#	WLS_PASSWD:weblogic����̨�û�����
#	WLS_JAR_PATH:weblogic.jar��·��
#	DOMAIN_PATH:weblogicӦ�÷����·��
#	BAK_PATH:����·��
#	NEW_PATH:�°�·��
#	DEPLOY_PATH:����·��
Deploy()
{
	echo "======���²���ʼ����������$SRVRNAME,WAR������$WARNAME======"
	echo "\n"
		
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
	done
	
	echo "��ʼ����weblogic����......"
	SRVRPATH=$DOMAIN_PATH/servers/$SRVRNAME
	echo "������Ŀ¼Ϊ:"$SRVRPATH
	echo "\n"
	echo "���������Ŀ¼��ɾ��[$SRVRPATH/tmp],[$SRVRPATH/stage]"
	if [ -d $SRVRPATH/tmp ]
	then
		rm -r $SRVRPATH/tmp > /dev/null
	else
	  echo "tmpĿ¼�����ڣ�����..."
	fi
	
	if [ -d $SRVRPATH/stage ]
	then
		rm -r $SRVRPATH/stage > /dev/null
	else
	  echo "stageĿ¼�����ڣ�����..."
	fi
		
	
	echo "��ʼ��������weblogic����......"
	
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
	
	echo "======���²���[$SRVRNAME,$WARNAME]�ɹ���======"
	echo "\n"
}	




echo "�������ű���ʼ......"

echo "\n"

###ȫ������###
echo "======������ȫ������======"

###�°�Ŀ¼###
NEW_PATH="/web/filenew"
echo "�°�Ŀ¼:[$NEW_PATH]"

###����Ŀ¼###
BAK_PATH="/web/filebak"
echo "����Ŀ¼:[$BAK_PATH]"

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

echo "======������΢����������======"

WEIBANK_WAR=weibank.war
echo "΢������Ӧ�ð���:[$WEIBANK_WAR]"

WEIBANK_SRVRNAME=weibank
echo "΢�����з�����:[$WEIBANK_SRVRNAME]"

echo "\n"

echo "======�����ǿ��Ż�������======"

OPENBANK_WAR=openbank.war
echo "���Ż���Ӧ�ð���:[$OPENBANK_WAR]"

OPENBANK_SRVRNAME=openbank-web
echo "���Ż���������:[$OPENBANK_SRVRNAME]"

echo "\n"

echo "======������֪ͨ��������======"

NOTIFY_WAR=openbank-notify.war
echo "֪ͨӦ�ð���:[$NOTIFY_WAR]"

NOTIFY_SRVRNAME=openbank-notify
echo "֪ͨ������:[$NOTIFY_SRVRNAME]"

echo "\n"

echo "======�����ǿ��Ż�����̨��������======"

MANAGER_WAR=openbank-manager.war
echo "��̨Ӧ�ð���:[$MANAGER_WAR]"

MANAGER_SRVRNAME=openbank-manager
echo "��̨������:[$MANAGER_SRVRNAME]"

echo "\n"

echo "======������֧�������񴰷�������======"

ALIPAYBANK_WAR=alipaybank.war
echo "֧��������Ӧ�ð���:[$ALIPAYBANK_WAR]"

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
	echo "\nA-ȫ�����񣨰�����̨��;\nB-��Ҫ���񣨲�������̨��;\nC-΢�����з���;\nD-���Ż�������;\nE-֪ͨ����;\nF-��̨����;\n��ѡ��Ҫ���²����Ӧ�ã�\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "�����²���ȫ�����񣨰�����̨����"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "�����²�����Ҫ���񣨲�������̨����"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "�����²���΢�����з���"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "�����²��𿪷Ż�������"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "�����²���֪ͨ����"
			flag="false"
			;;
		f|F)
			echo "\n......\n"
			echo "�����²����̨����"
			flag="false"
			;;
		g|G)
			echo "\n......\n"
			echo "�����²���֧�������񴰷���"
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

echo "\n...����ʼ...\n"

case "$deploy_option" in
	a|A)
		SRVRNAME=$WEIBANK_SRVRNAME
		WARNAME=$WEIBANK_WAR
		UpdateWar
		SRVRNAME=$OPENBANK_SRVRNAME
		WARNAME=$OPENBANK_WAR
		UpdateWar
		SRVRNAME=$NOTIFY_SRVRNAME
		WARNAME=$NOTIFY_WAR
		UpdateWar
		SRVRNAME=$MANAGER_SRVRNAME
		WARNAME=$MANAGER_WAR
		UpdateWar

		SRVRNAME=$WEIBANK_SRVRNAME
		WARNAME=$WEIBANK_WAR
		Deploy
		SRVRNAME=$OPENBANK_SRVRNAME
		WARNAME=$OPENBANK_WAR
		Deploy
		SRVRNAME=$NOTIFY_SRVRNAME
		WARNAME=$NOTIFY_WAR
		Deploy
		SRVRNAME=$MANAGER_SRVRNAME
		WARNAME=$MANAGER_WAR
		Deploy
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		WARNAME=$ALIPAYBANK_WAR
		Deploy
		;;
	b|B)
		SRVRNAME=$WEIBANK_SRVRNAME
		WARNAME=$WEIBANK_WAR
		UpdateWar
		SRVRNAME=$OPENBANK_SRVRNAME
		WARNAME=$OPENBANK_WAR
		UpdateWar
		SRVRNAME=$NOTIFY_SRVRNAME
		WARNAME=$NOTIFY_WAR
		UpdateWar

		SRVRNAME=$WEIBANK_SRVRNAME
		WARNAME=$WEIBANK_WAR
		Deploy
		SRVRNAME=$OPENBANK_SRVRNAME
		WARNAME=$OPENBANK_WAR
		Deploy
		SRVRNAME=$NOTIFY_SRVRNAME
		WARNAME=$NOTIFY_WAR
		Deploy
		;;
	c|C)
		SRVRNAME=$WEIBANK_SRVRNAME
		WARNAME=$WEIBANK_WAR
		UpdateWar
		Deploy
		;;
	d|D)
		SRVRNAME=$OPENBANK_SRVRNAME
		WARNAME=$OPENBANK_WAR
		UpdateWar
		Deploy
		;;		
	e|E)
		SRVRNAME=$NOTIFY_SRVRNAME
		WARNAME=$NOTIFY_WAR
		UpdateWar
		Deploy
		;;
	f|F)
		SRVRNAME=$MANAGER_SRVRNAME
		WARNAME=$MANAGER_WAR
		UpdateWar
		Deploy
		;;
	g|G)
		SRVRNAME=$ALIPAYBANK_SRVRNAME
		WARNAME=$ALIPAYBANK_WAR
		UpdateWar
		Deploy
		;;
esac
