call mvn install:install-file -DgroupId=rt -DartifactId=rt -Dversion=1.6 -Dpackaging=jar -Dfile=%~dp0rt.jar 
call mvn install:install-file -DgroupId=com.alipay.api -DartifactId=alipay -Dversion=1.0 -Dpackaging=jar -Dfile=%~dp0alipay.jar 