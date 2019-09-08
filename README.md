# socket.io.java.client.biz

![][license-url]

DFocus wanted ssp solution - java-client.

`socket.io.java.client.biz` is a porting version of [socket.io.client.biz](https://raw.githubusercontent.com/DFocusFE/socket.io.client.biz), which is made for domain specific business scenarios. It consists of following features:

- re-connect
- authentication via token
- project based, let's say you are working on a SaaS platform, several projects may subscribe topics individually
- easy to distinguish events from topics
- no need to worry about re-subscribe process whenever re-connect triggered

## Depdencies

### maven

```xml
<dependency>
    <groupId>com.dfocus</groupId>
    <artifactId>socket.io.java.client.biz</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

> Currently, you have to install the library to your local repo, with `bash shells/install.sh`

## Usage

```java
public class SocketIoClientBizTest {
    public static void main(String[] args) {
        SocketOpts opts = new SocketOpts("http://hi.dfocus.com", "your projectId", "your token");
        SocketIoClientBiz biz = new SocketIoClientBiz(opts);

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
    }
}
```

## LICENSE

[MIT License](https://raw.githubusercontent.com/DFocusFE/socket.io.java.client.biz/master/LICENSE)

[license-url]: https://img.shields.io/github/license/dfocusfe/socket.io.java.client.biz
