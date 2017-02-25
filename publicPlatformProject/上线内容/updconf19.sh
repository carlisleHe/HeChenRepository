cd /apache/dist/
tar cf conf.bak.tar conf
mv /apache/filenew/conf.19.tar ./conf.tar 
rm -rf conf/
tar xf conf.tar