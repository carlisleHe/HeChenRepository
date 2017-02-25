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

#function:Start启动服务
#params:
#	SRVRNAME:服务名称
#	ADMIN_URL:管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
Start()
{		
	flag="true"

	echo "确认$SRVRNAME服务已经关闭......"
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
				echo "未知的服务器状态，强制执行关闭指令！"
				echo "开始强制关闭服务器......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  FORCESHUTDOWN "$SRVRNAME"		
				RSLT=$?
				if [ RSLT -ne 0 ]
				then
					echo "停止服务器出错，操作退出！"
					echo "\n"
					exit 1
				fi
			##若服务器状态为0(运行中)
			else
				echo "开始正常关闭服务器......"
				java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD" -timeout 10  SHUTDOWN "$SRVRNAME"		
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
  
  echo "开始启动[$SRVRNAME]服务......"
	
	java -classpath "$WLS_JAR_PATH" weblogic.Admin -adminurl "$ADMIN_URL" -username "$WLS_USER" -password "$WLS_PASSWD"  START "$SRVRNAME"		
	RSLT=$?
	if [ RSLT -ne 0 ]
	then
		echo "启动服务器出错！请确认相关指令"
		echo "\n"
		exit 1
	else
		GetSrvrState
		if [ $SrvrState != 0 ]
		then 
			echo "无法正常启动服务器，操作退出！"
			echo "\n"
			exit 1
		fi
	fi
	
	echo "\n"
  echo "======启动[$SRVRNAME]成功！======"
	echo "\n"
}	

#function:StartAdmin启动AdminServer
#params:
#	SRVRNAME:服务名称
#	ADMIN_URL:管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
StartAdmin()
{		
  cd $DOMAIN_PATH
  nohup sh ./startWebLogic.sh >>nohup.out 2>&1 &
  aflag="true"
  sleep 20
  while [ "$aflag" = "true" ]
	do
	  #看AdminServer是否启动成功
	  sleep 10
	  GetSrvrState
	  if [ $SrvrState == 0 ]
		then
		  aflag="false"
		else
		  echo "检测AdminServer状态失败，10秒后重试..."
		fi
	done
	echo "\n"
  echo "======启动[$SRVRNAME]成功！======"
	echo "\n"
}	



echo "启动服务脚本开始......"

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

###开放互联服务设置###
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

###支付宝服务窗服务设置###
ALIPAYBANK_SRVRNAME=alipaybank
echo "支付宝服务窗服务名:[$ALIPAYBANK_SRVRNAME]"

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
	echo "\nA-全部应用（包括后台）;\nB-主要应用（不包括后台）;\nC-微信银行服务;\nD-开放互联服务;\nE-通知服务;\nU-支付宝服务窗服务;\nF-后台服务; \nS-AdminServer管理服务；\nP-主要应用(不包括后台)和AdminServer；\nT-所有应用和AdminServer；\n请选择要启动的应用：\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "将启动全部应用服务（包括后台）！"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "将启动主要服务（不包括后台）！"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "将启动微信银行服务！"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "将启动开放互联服务！"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "将启动通知服务！"
			flag="false"
			;;
	  f|F)
			echo "\n......\n"
			echo "将启动后台服务！"
			flag="false"
			;;
		s|S)
			echo "\n......\n"
			echo "将启动AdminServer(管理服务)！"
			flag="false"
			;;
		p|P)
			echo "\n......\n"
			echo "将启动主要服务(不包括后台)和AdminServer！"
			flag="false"
			;;
		t|T)
			echo "\n......\n"
			echo "将启动所有服务(包括后台)和AdminServer！"
			flag="false"
			;;
		u|U)
			echo "\n......\n"
			echo "将启动支付宝服务窗服务！"
			flag="false"
			;;
		*)
			echo "\n......\n"
			echo "错误的选项！请重新输入...\n"
			continue
			;;
	esac
done

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

echo "\n...开始启动服务...\n"

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
	    echo "\n...开始启动AdminServer..."
		SRVRNAME=$ADMIN_SRVRNAME
		StartAdmin
		echo "\n...开始启动受管服务..."
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
	  echo "\n...开始启动AdminServer..."
		SRVRNAME=$ADMIN_SRVRNAME
		StartAdmin
		echo "\n...开始启动受管服务..."
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


echo "====== 开启脚本执行完毕 ======"
