#!/bin/sh

#0-RUNNING,1-SHUTDOWN,2-OTHERS
SrvrState=0
#function:GetSrvrState 获取当前对应weblogic服务器状态 
#params:
#	SRVRNAME:对应服务名称
#	ADMIN_URL:对应管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
# return SrvrState,其中0-RUNNING,1-SHUTDOWN,2-OTHERS
GetSrvrState(){
	java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" GETSTATE "$SRVRNAME"|read tmp
	RSLT=$?
	if [ RSLT -ne 0 ]
	then
		echo "无法正确连接服务器，并获取服务器状态，操作退出！"
		echo "\n"
		exit 1
	else
		echo "服务器返回信息："$tmp
		echo "\n"
		echo $tmp |awk '{print $6}'|read tmp
		case $tmp in
			SHUTDOWN)
				echo "服务器当前状态为关闭[$tmp]！"
				echo "\n"
				SrvrState=1
				;;
			RUNNING)
				echo "服务器当前状态为运行中[$tmp]！"
				echo "\n"
				SrvrState=0
				;;
			*)
				echo "服务器当前状态为未知[$tmp]！"
				echo "\n"
				SrvrState=2
				;;
		esac
	fi
}

#function:Stop关闭服务
#params:
#	SRVRNAME:服务名称
#	ADMIN_URL:管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
Stop()
{		
	echo "停止并确认已经关闭$SRVRNAME服务......"
	echo "\n"
	
	flag="true"
	while [ "$flag" = "true" ]
	do
		GetSrvrState
		if [ $SrvrState != 1 ]
		then
			##若服务器状态为未知（非SHUTDOWN,RUNNING）
			if [ $SrvrState = 2 ]
			then
				echo "未知的服务器状态，退出或强制执行关闭指令！(y强制执行|n退出)\c"
				read option
				case "$option" in 
					y|Y)
						echo "开始强制关闭服务器......"
						java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
						RSLT=$?
						if [ RSLT -ne 0 ]
						then
							echo "停止服务器出错，操作退出！"
							echo "\n"
							exit 1
						fi
						;;
					*)
						echo "\n"
						echo "接受退出指令，操作退出！"
						echo "\n"
						exit 1
						;;
				esac
			##若服务器状态为0(运行中)
			else
				echo "开始正常关闭服务器......"
	      echo "将使用 ${SHUTDOWN_CMD} 关闭服务，请耐心等待......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 10  $SHUTDOWN_CMD "$SRVRNAME"		
				RSLT=$?
				if [ RSLT -ne 0 ]
				then
					echo "停止服务器出错，操作退出！"
					echo "\n"
					exit 1
				fi
			fi		
		else
			flag="false"
		fi
	done
	
	echo "开始清理weblogic缓存......"
	SRVRPATH=$DOMAIN_PATH/servers/$SRVRNAME
	echo "服务器目录为:"$SRVRPATH
	echo "\n"
	echo "清理服务器目录，删除[$SRVRPATH/tmp],[$SRVRPATH/stage]"
	if [ -d "$SRVRPATH/tmp" ]; then 
	  rm -r $SRVRPATH/tmp > /dev/null
	fi
	if [ -d "$SRVRPATH/stage" ]; then 
		rm -r $SRVRPATH/stage > /dev/null
  fi
	echo "\n"
  echo "======关闭[$SRVRNAME]成功！======"
	echo "\n"
}	

#function:StopAdmin关闭AdminServer
#params:
#	SRVRNAME:服务名称
#	ADMIN_URL:管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
StopAdmin()
{		
	echo "停止并确认已经关闭$SRVRNAME服务......"
	echo "\n"
	
  GetSrvrState
	if [ $SrvrState != 1 ]
	then
		##若服务器状态为未知（非SHUTDOWN,RUNNING）
		if [ $SrvrState = 2 ]
		then
			echo "未知的服务器状态，退出或强制执行关闭指令！(y强制执行|n退出)\c"
			read option
			case "$option" in 
				y|Y)
					echo "开始强制关闭服务器......"
					java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
					RSLT=$?
					if [ RSLT -ne 0 ]
					then
						echo "停止服务器出错，操作退出！"
						echo "\n"
						exit 1
					fi
					;;
				*)
					echo "\n"
					echo "接受退出指令，操作退出！"
					echo "\n"
					exit 1
					;;
			esac
		##若服务器状态为0(运行中)
		else
			echo "开始正常关闭服务器......"
      echo "将使用 ${SHUTDOWN_CMD} 关闭服务，请耐心等待......"
			java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 300  $SHUTDOWN_CMD "$SRVRNAME"		
			RSLT=$?
			if [ RSLT -ne 0 ]
			then
				echo "停止服务器出错，操作退出！"
				echo "\n"
				exit 1
			fi
		fi		
	else
		flag="false"
	fi
	
	echo "\n"
  echo "======关闭[$SRVRNAME]成功！======"
	echo "\n"
}	



echo "关闭服务脚本开始......"

echo "\n"

###全局设置###
echo "======以下是全局设置======"

###生产包部署目录###
DEPLOY_PATH=/web/webapps/war
echo "生产包部署目录:[$DEPLOY_PATH]"

###管理控制台地址###
ADMIN_URL=t3://localhost:7001
echo "管理控制台地址:[$ADMIN_URL]" 

###weblogic应用服务根路径###
DOMAIN_PATH=/web/domains/openbank
echo "weblogic应用服务根路径:[$DOMAIN_PATH]"

###weblogic.jar路径###
WLS_JAR_PATH=/web/Oracle/Middleware/wlserver_10.3/server/lib/weblogic.jar

echo "\n"

###微信银行服务设置###
WEIBANK_SRVRNAME=weibank
echo "微信银行服务名:[$WEIBANK_SRVRNAME]"

###通知服务设置###
OPENBANK_SRVRNAME=openbank-web
echo "开放互联服务名:[$OPENBANK_SRVRNAME]"

###通知服务设置###
NOTIFY_SRVRNAME=openbank-notify
echo "通知服务名:[$NOTIFY_SRVRNAME]"

###后台管理设置###
MNGR_SRVRNAME=openbank-manager
echo "后台服务名:[$MNGR_SRVRNAME]"

###AdminServer设置###
ADMIN_SRVRNAME=AdminServer
echo "AdminServer服务名:[$ADMIN_SRVRNAME]"

###AdminServer设置###
ALIPAYBANK_SRVRNAME=alipaybank
echo "支付宝钱包服务窗服务名:[$ALIPAYBANK_SRVRNAME]"

echo "\n"

echo "以上信息正确吗?(y继续/n中断):\c"
read option

case "$option" in 
	y|Y)
		;;
	*)
	echo "\n"
	echo "配置不符，中断退出，请确认相关配置后重新执行！"
	exit 0
		;;
	esac

echo "\n......\n"

flag="true"
deploy_option="A"

while [ "$flag" = "true" ]
do
	echo "\nA-全部应用（包括后台）;\nB-主要应用（不包括后台）;\nC-微信银行服务;\nD-开放互联服务;\nE-通知服务;\nU-支付宝钱包服务窗;\nF-后台服务; \nS-AdminServer管理服务；\nP-主要服务(不包括后台)和AdminServer；\nT-所有服务和AdminServer；\n\n请选择要关闭的应用：\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "将关闭全部应用服务（包括后台）！"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "将关闭主要应用服务（不包括后台）！"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "将关闭微信银行服务！"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "将关闭开放互联服务！"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "将关闭通知服务！"
			flag="false"
			;;
		f|F)
			echo "\n......\n"
			echo "将关闭后台服务！"
			flag="false"
			;;
		s|S)
			echo "\n......\n"
			echo "将关闭AdminServer管理服务！"
			flag="false"
			;;		
		p|P)
			echo "\n......\n"
			echo "将关闭主要服务(不包括后台)和AdminServer！"
			flag="false"
			;;	
		t|T)
			echo "\n......\n"
			echo "将关闭所有服务(包括后台)和AdminServer！"
			flag="false"
			;;
		u|U)
			echo "\n......\n"
			echo "将关闭支付宝钱包服务窗！"
			flag="false"
			;;
		*)
			echo "\n......\n"
			echo "错误的选项！请重新输入...\n"
			continue
			;;
	esac
done

echo "\n"
echo "是否使用"FORCESHUTDOWN"强制关闭服务?(y是/n否):\c"
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
		echo "配置不符，中断退出，请确认后重新执行！"
		exit 0
		;;
esac

echo "\n......\n"

echo "Weblogic控制台用户名:weblogic"
WLS_USER="weblogic"
echo "\n"

while [ -z "$WLS_PASSWD" ]
do
	stty -echo
	echo $WLS_USER"用户密码:\c"
	read WLS_PASSWD
	stty echo
	if [ -z "$WLS_PASSWD" ] 
	then
		echo "控制台密码不能为空, 请重新输入!" 
	fi
	echo "\n"
done

echo "\n...开始关闭服务...\n"

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
