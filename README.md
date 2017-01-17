# Test of an nodejs, Reactos, express and express-stormpath application

#### 1. Launch selenium GRID (version 3.0.1)
```bash
$ docker run -d -p 4444:4444 --name selenium-hub selenium/hub:3.0.1-aluminum
$ docker run -d --link selenium-hub:hub selenium/node-chrome:3.0.1-aluminum
$ docker run -d --link selenium-hub:hub selenium/node-firefox:3.0.1-aluminum
```
#### 2. Launch Web application
```bash
$ cd <root directory of WebApplication>
$ npm install (only first time)
$ npm start
```
#### 3. Launch Test
##### 3.1. file configuration
~/.stormpath/apiKey.properties well configurated is needed

```bash
mvn test
```

## HowTo Install Oracle Java on Ubuntu/ Debian

###### [Istituto Majorana. Howto install java](http://www.istitutomajorana.it/index.php?option=com_content&task=view&id=2148&Itemid=33) (Italian Doc)
###### [HowTo intall java](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get) (English Doc)

```bash
sudo apt-get install python-software-properties
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
# I use java 8 (Java 9 is already available, but it is an early version)
sudo apt-get install oracle-java8-installer
sudo update-alternatives --config java
```
