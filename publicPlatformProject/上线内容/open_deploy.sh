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

#function:UpdateWar 更新部署包
#params:
#	SRVRNAME:服务名称
#	WARNAME:将被部署的war包名称
#	BAK_PATH:备份路径
#	NEW_PATH:新包路径
#	DEPLOY_PATH:部署路径
UpdateWar()
{
	echo "======更新部署包开始！服务名称$SRVRNAME,WAR包名称$WARNAME======"
	echo "\n"
	
	DEPLOYWAR_PATH=$DEPLOY_PATH/$WARNAME
	echo "安装包路径："$DEPLOYWAR_PATH
	BAKWAR_PATH=$BAK_PATH/$WARNAME.`date +%Y%m%d`
	echo "备份包路径："$BAKWAR_PATH
	NEWWAR_PATH=$NEW_PATH/$WARNAME
	echo "新包路径："$NEWWAR_PATH
	echo "\n"

	echo "部署新包......"
	echo "\n"
	echo "请检查新包大小"
	ls -l $NEWWAR_PATH
	
	echo "按enter键继续...\c"
	read option
	echo "\n"
	
	
	echo "是否进行包备分！(y备分|n不备分)\c"
	read option
	case "$option" in 
		y|Y)
			echo "备份现有应用......"
			echo "\n"
			echo "开始拷贝：from $DEPLOYWAR_PATH to $BAKWAR_PATH ....."
			cp $DEPLOYWAR_PATH $BAKWAR_PATH
			echo "\n"
			;;
		*)
			;;
	esac
	
	echo "\n"
	echo "开始拷贝：from $NEWWAR_PATH to $DEPLOYWAR_PATH ......"
	cp $NEWWAR_PATH $DEPLOYWAR_PATH
	
	echo "\n"
	echo "请核对新安装包大小"
	ls -l $NEWWAR_PATH
	ls -l $DEPLOYWAR_PATH
	
	echo "按enter键继续...\c"
	read option
	echo "\n"
	
	echo "======更新部署包[$SRVRNAME,$WARNAME]成功！======"
	echo "\n"
}

#function:Deploy 备份并重新部署新包
#params:
#	SRVRNAME:服务名称
#	WARNAME:将被部署的war包名称
#	ADMIN_URL:管理URL
#	WLS_USER:weblogic控制台用户
#	WLS_PASSWD:weblogic控制台用户密码
#	WLS_JAR_PATH:weblogic.jar包路径
#	DOMAIN_PATH:weblogic应用服务根路径
#	BAK_PATH:备份路径
#	NEW_PATH:新包路径
#	DEPLOY_PATH:部署路径
Deploy()
{
	echo "======重新部署开始！服务名称$SRVRNAME,WAR包名称$WARNAME======"
	echo "\n"
		
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
	done
	
	echo "开始清理weblogic缓存......"
	SRVRPATH=$DOMAIN_PATH/servers/$SRVRNAME
	echo "服务器目录为:"$SRVRPATH
	echo "\n"
	echo "清理服务器目录，删除[$SRVRPATH/tmp],[$SRVRPATH/stage]"
	if [ -d $SRVRPATH/tmp ]
	then
		rm -r $SRVRPATH/tmp > /dev/null
	else
	  echo "tmp目录不存在，跳过..."
	fi
	
	if [ -d $SRVRPATH/stage ]
	then
		rm -r $SRVRPATH/stage > /dev/null
	else
	  echo "stage目录不存在，跳过..."
	fi
		
	
	echo "开始重新启动weblogic服务......"
	
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
	
	echo "======重新部署[$SRVRNAME,$WARNAME]成功！======"
	echo "\n"
}	




echo "部署服务脚本开始......"

echo "\n"

###全局设置###
echo "======以下是全局设置======"

###新包目录###
NEW_PATH="/web/filenew"
echo "新包目录:[$NEW_PATH]"

###备份目录###
BAK_PATH="/web/filebak"
echo "备份目录:[$BAK_PATH]"

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

echo "======以下是微信银行设置======"

WEIBANK_WAR=weibank.war
echo "微信银行应用包名:[$WEIBANK_WAR]"

WEIBANK_SRVRNAME=weibank
echo "微信银行服务名:[$WEIBANK_SRVRNAME]"

echo "\n"

echo "======以下是开放互联设置======"

OPENBANK_WAR=openbank.war
echo "开放互联应用包名:[$OPENBANK_WAR]"

OPENBANK_SRVRNAME=openbank-web
echo "开放互联服务名:[$OPENBANK_SRVRNAME]"

echo "\n"

echo "======以下是通知服务设置======"

NOTIFY_WAR=openbank-notify.war
echo "通知应用包名:[$NOTIFY_WAR]"

NOTIFY_SRVRNAME=openbank-notify
echo "通知服务名:[$NOTIFY_SRVRNAME]"

echo "\n"

echo "======以下是开放互联后台服务设置======"

MANAGER_WAR=openbank-manager.war
echo "后台应用包名:[$MANAGER_WAR]"

MANAGER_SRVRNAME=openbank-manager
echo "后台服务名:[$MANAGER_SRVRNAME]"

echo "\n"

echo "======以下是支付宝服务窗服务设置======"

ALIPAYBANK_WAR=alipaybank.war
echo "支付宝服务窗应用包名:[$ALIPAYBANK_WAR]"

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
	echo "\nA-全部服务（包含后台）;\nB-主要服务（不包含后台）;\nC-微信银行服务;\nD-开放互联服务;\nE-通知服务;\nF-后台服务;\n请选择要重新部署的应用：\a"	
	read deploy_option
	case "$deploy_option" in
		a|A)
			echo "\n......\n"
			echo "将重新部署全部服务（包含后台）！"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "将重新部署主要服务（不包含后台）！"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "将重新部署微信银行服务！"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "将重新部署开放互联服务！"
			flag="false"
			;;
		e|E)
			echo "\n......\n"
			echo "将重新部署通知服务！"
			flag="false"
			;;
		f|F)
			echo "\n......\n"
			echo "将重新部署后台服务！"
			flag="false"
			;;
		g|G)
			echo "\n......\n"
			echo "将重新部署支付宝服务窗服务！"
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

echo "\n...部署开始...\n"

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
