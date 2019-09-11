---
home: true
actionText: More Information
actionLink: /api/
---

[![NPM version][npm-image]][npm-url]
![][david-url]
![][dt-url]
![][license-url]

DFocus wanted ssp solution - java-client. It is a porting version of [socket.io.client.biz](https://github.com/DFocusFE/socket.io.client.biz).

It must be work with the specific Backend(TBD)

`socket.io.java.client.biz` is made for domain specific business scenarios. It consists of following features:

- re-connect
- authentication via token
- project based, let's say you are working on a SaaS platform, several projects may subscribe topics individually
- easy to distinguish events from topics
- no need to worry about re-subscribe process whenever re-connect triggered

## Install

Currently, you can only use `socket.io.java.client.biz` from the [source](https://github.com/DFocusFE/socket.io.java.client.biz).

```bash
# clone source code
git clone https://github.com/DFocusFE/socket.io.java.client.biz.git
cd socket.io.java.client.biz.git

# install the library into your local maven repo
mvn clean install
```

**maven**

```xml
<dependency>
    <groupId>com.dfocus</groupId>
    <artifactId>socket.io.java.client.biz</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

**gradle**

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

[MIT License](https://raw.githubusercontent.com/DFocusFE/socket.io.client.biz/master/LICENSE)

[npm-url]: https://npmjs.org/package/socket.io.client.biz
[npm-image]: https://badge.fury.io/js/socket.io.client.biz.png
[david-url]: https://david-dm.org/DFocusFE/socket.io.client.biz.png
[dt-url]: https://img.shields.io/npm/dt/socket.io.client.biz.svg
[license-url]: https://img.shields.io/npm/l/socket.io.client.biz.svg