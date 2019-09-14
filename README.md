# socket.io.java.client.biz

[![Build Status](https://travis-ci.org/DFocusGroup/socket.io.java.client.biz.png?branch=master)](https://travis-ci.org/DFocusGroup/socket.io.java.client.biz)
[![Codecov](https://codecov.io/gh/DFocusGroup/socket.io.java.client.biz/branch/master/graph/badge.svg)](https://codecov.io/gh/DFocusGroup/socket.io.java.client.biz/branch/master)
![][license-url]

DFocus wanted ssp solution - java-client.

`socket.io.java.client.biz` is a porting version of [socket.io.client.biz](https://raw.githubusercontent.com/DFocusGroup/socket.io.client.biz), which is made for domain specific business scenarios. It consists of following features:

- damn easy, only 4 methods, that's all you need
- re-connect
- authentication via token
- project based, let's say you are working on a SaaS platform, several projects may subscribe topics individually
- easy to distinguish events from topics
- no need to worry about re-subscribe process whenever network issue happened

## Depdencies

Currently, you can only use `socket.io.java.client.biz` from the [source](https://github.com/DFocusGroup/socket.io.java.client.biz).

```bash
# clone source code
git clone https://github.com/DFocusGroup/socket.io.java.client.biz.git
cd socket.io.java.client.biz.git

# install the library into your local maven repo
mvn clean install
```

### maven

```xml
<dependency>
    <groupId>com.dfocus</groupId>
    <artifactId>socket.io.java.client.biz</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### gradle

Add `mavenLocal()` to `build.gradle`

```
allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
}
```

Add dependency

```
dependencies {
    implementation 'com.dfocus:socket.io.java.client.biz:1.0-SNAPSHOT'
}
```

## Usage

```java
public class SocketIoClientBizTest {
    public static void main(String[] args) {
        SocketOpts opts = new SocketOpts("http://hi.dfocus.com", "your projectId", "your token");
        SocketIoClientBiz biz = new SocketIoClientBiz(opts);

        try{
            biz.connect(new Finish() {
                @Override
                public void onFinished(String msg) {
                    if ("".equals(msg)) {
                        System.out.println("Connection established");
                    } else {
                        System.out.println("Failed to connect to server: " + msg);
                    }

                }
            });

            biz.subscribe("your topic", "your event", new EventCallback() {
                @Override
                public void onFire(final EventMessage message) {
                    @Override
                    public void run() {
                        System.out.println("Message from server: " + message.getPayload());
                    }
                }
            });

            biz.onStateChange(new StateChangeCallback() {
                @Override
                public void onChange(final ClientState s) {
                    System.out.println("State Changed: " + s);
                }
            });
        }
        catch(InvalidArgumentException e) {
            e.printStackTrace();
        }
        catch(LifecycleException e) {
            e.printStackTrace();
        }
    }
}
```

> If you are going to use it with Android, all callbacks has to be called in `runOnUiThread`

## LICENSE

[MIT License](https://raw.githubusercontent.com/DFocusGroup/socket.io.java.client.biz/master/LICENSE)

[license-url]: https://img.shields.io/github/license/DFocusGroup/socket.io.java.client.biz
