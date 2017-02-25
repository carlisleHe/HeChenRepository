#!/bin/sh

#function:UpdateRes 更新资源包
#params:
# RES_PATH:资源地址
#	TAR_NAME:服务名称
UpdateRes()
{
	echo "======更新Apache资源包开始！======"
	echo "资源文件目录:[$RES_PATH]"
  echo "资源包名称:[$TAR_NAME]"
	
	echo "\n"
	
	DEPLOY_PATH=$APA_PATH/$TAR_NAME
	echo "安装包路径："$DEPLOY_PATH
	BAKTAR_PATH=$BAK_PATH/$TAR_NAME.`date +%Y%m%d`
	echo "备份包路径："$BAKTAR_PATH
	NEWTAR_PATH=$NEW_PATH/$TAR_NAME
	echo "新包路径："$NEWTAR_PATH
	echo "\n"
	echo "部署新包......"
	echo "\n"
	echo "请检查新包大小"
	ls -l $NEWTAR_PATH
	
	echo "确认下发包大小无误后，请按enter键继续...\c"
	read option
	echo "\n"
	
	echo "是否备份现有资源文件！(y备分|n不备分)\c"
	read option
	case "$option" in 
		y|Y)
			echo "备份现有资源文件......"
			echo "\n"
			echo "开始打包资源文件，正在执行：tar cvf $BAKTAR_PATH $RES_PATH......"
			tar cvf $BAKTAR_PATH $RES_PATH
	
			echo "\n"
			echo "资源文件打包备份已成功"
			ls -l $BAKTAR_PATH
			echo "\n"
			;;
		*)
			;;
	esac
	
	echo "\n"
	echo "开始拷贝：from $NEWTAR_PATH to $DEPLOY_PATH ......"
	cp $NEWTAR_PATH $DEPLOY_PATH
	
	echo "\n"
	echo "请核对新安装包大小是否一致"
	ls -l $NEWTAR_PATH
	ls -l $DEPLOY_PATH
	
	echo "按enter键继续...\c"
	read option
	echo "\n"
	
	echo "开始更新Apache静态资源文件......"
	cd $APA_PATH
	tar xvf $TAR_NAME
	
}


echo "更新Apache资源文件开始......"

echo "\n"

###全局设置###
echo "======以下是全局设置======"

###新包目录###
NEW_PATH="/apache/filenew"
echo "新包目录:[$NEW_PATH]"

###备份目录###
BAK_PATH="/apache/filebak"
echo "备份目录:[$BAK_PATH]"

###Apache主目录###
APA_PATH="/apache/dist"
echo "Apache主目录:[$APA_PATH]"

flag="true"
update_option="A"

while [ "$flag" = "true" ]
do
	echo "\nA-全部 ;\nB-微信银行;\nC-开放互联;\nD-支付宝服务窗;\n\n请选择要更新资源的应用：\a"	
	read update_option
	case "$update_option" in
		a|A)
			echo "\n......\n"
			echo "将更新全部应用资源！"
			flag="false"
			;;
		b|B)
			echo "\n......\n"
			echo "将更新微信银行应用资源！"
			flag="false"
			;;
		c|C)
			echo "\n......\n"
			echo "将更新开放互联应用资源！"
			flag="false"
			;;
		d|D)
			echo "\n......\n"
			echo "将更新支付宝服务窗应用资源！"
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

echo "\n...更新开始...\n"

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

echo "======更新Apache资源文件脚本执行结束！======"	
echo "\n"