---
sidebarDepth: 4
---

# API

## SocketIoClientBiz

`(SocketOpts opts) => SocketIoClientBiz`

- construct a client instance

**Usage**

```java
SocketOpts opts = new SocketOpts("http://mock.dfocus.com", "your projectId", "your token");
SocketIOFactory factory = new SocketIOFactory(opts);
SocketIoClientBiz biz = new SocketIoClientBiz(factory);
```

## Methods

### connect

`(Finish callback) => void`

- open the connection with server

**Usage**

```java
biz.connect(new Finish() {
    @Override
    public void onFinished(String errorMessage) {
		if ("".equals(errorMessage)) {
			System.out.println("connected");
		} else {
			System.out.println("failed to connect");
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
Subscription stateChangeSub = biz.onStateChange(new StateChangeCallback() {
    @Override
    public void onChange(ClientState s) {
		System.out.println("state changed to " + s);
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
Subscription eventSub = biz.subscribe("spaces", "SPACE_ADDED", new EventCallback() {
    @Override
    public void onFire(EventMessage message) {
        System.out.println("event = " + message.getPayload());
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
biz.disconnect();
```
