# How to deploy this app

1. Get the **ecinema** app from repository
2. Download the latest version of **mod_jk.so** and copy it to **$APACHE_HOME/modules/**
3. Copy **mod_jk.conf** and **workers.properties** from **config** folder to **$APACHE_HOME/conf/extra/**
4. Add to **httpd.conf** the following lines:

    LoadModule jk_module modules/mod_jk.so
    
    Include conf/extra/mod_jk.conf
    
    DocumentRoot "c:/apache_cache"
    <Directory "c:/apache_cache">
        Options Indexes FollowSymLinks
        Order allow,deny
        Allow from all
        AllowOverride None
        Require all granted
    </Directory>
    
    <VirtualHost localhost:80>
      ServerName localhost
      DocumentRoot "c:/apache_cache"
      <Directory "c:/apache_cache">
        Order allow,deny
        Allow from all
        AcceptPathInfo On
      </Directory>
      <IfModule mod_jk.c>
        JkMountCopy On
      </IfModule>
    </VirtualHost>

5. Build the **ecinema** app and deploy it to Tomcat
6. Copy the contents of **ecinema/src/main/webapp/resources** folder to **c:/apache_cache/resources**
7. Go to http://localhost/ecinema