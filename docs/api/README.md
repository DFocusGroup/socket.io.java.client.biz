---
sidebarDepth: 4
---

# API

## SocketIoClientBiz

`(SocketOpts opts) => SocketIoClientBiz`

- construct a client instance

**Usage**

```java
SocketOpts opts = new SocketOpts("http://hi.dfocus.com", "your projectId", "your token");
SocketIoClientBiz biz = new SocketIoClientBiz(opts);
```

## Methods

### connect

`(Finish callback) => void`

- open the connection with server

**Usage**

```java
bizClient.connect(new Finish() {
    @Override
    public void onFinished(final String errorMessage) {
        @Override
        public void run() {
            if ("".equals(errorMessage)) {
                System.out.println("connected");
            } else {
                System.out.println("failed to connect");
            }
        }
    }
});
```

### onStateChange

`(ClientState state) => Subscription`

- listen for client state change

**Usage**

```java
// watch every connection state change
const stateChangeSub = bizClient.onStateChange(new StateChangeCallback() {
    @Override
    public void onChange(ClientState s) {
        @Override
        public void run() {
            System.out.println("state changed to " + s);
        }
    }
});

// you can dispose this subscription later
stateChangeSub.dispose();
```

### subscribe

`(String topic, String event, EventCallback cb)=> Subscription`

- subscribe event for specific topic

**Usage**

```java
// watch for specific event along with its topic
const eventSub = bizClient.subscribe("spaces", "SPACE_ADDED", new EventCallback() {
    @Override
    public void onFire(EventMessage message) {
        @Override
        public void run() {
            System.out.println("event = " + message.getPayload());
        }
    }
});

// you can dispose this subscription later
eventSub.dispose();
```

### disconnect

`(): void`

- manually close the connection with server side

**Usage**

```java
bizClient.disconnect();
```
