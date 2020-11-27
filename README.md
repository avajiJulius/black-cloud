Black Cloud  [![Codeship Status for avajiJulius/black-cloud](https://app.codeship.com/projects/1c337082-3752-470f-a65c-eab43bee4fa1/status?branch=master)](https://app.codeship.com/projects/419920)
=====================
- [Stucure and steps](#structure-and-steps)
- [Modules](#modules)
    - [Black Cloud Desktop Application](#black-cloud-desktop-application)
    - [Security](#security)
---------------------

## Structure and Steps

1) [Black Cloud Desktop Application](#black-cloud-desktop-application)
2) [Security(Cryptography)](#security)
3) Server-client messanger
4) Cloud storage

## Modules

### Black Cloud Desktop Application

Have 3 panel for managing the files

| |Local|Secure|Cloud|
|:---:|:---:|:---:|:---:|
|Location|Local computer|Secure folder|Cloud Storage|
|Export| to 'secure' | to 'cloud' | - |  
|Import| - | to 'local' | to 'secure' |
|Functionality| files manage | unziping, decrypting files from 'cloud'; ziping, encrypting files| store, manage files |

Controller contains buttons actions. 
Method that describe this actions check focused panel and selected item.
```java
PanelController pc;
if( pc.getSelectedFilename() != null && pc.isFocused()) {
...
```
Define ActionService with setting certain ActionHandler by passing panel, panels or panels with zipping and encrypting classes.
```java
actionService.setActionHandler(new LocalActionHandler(pc));
```
ZipService, EncryptService and ActionService define in initialize method in Controller.
```java
 public void initialize(URL url, ResourceBundle resourceBundle) {
        zipService = new ZipService();
        actionService = new ActionService(null);
        ...
    }
```

ActionService class has ActionHandler parameter
```java
public ActionService(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }
```
ActionHandler interface has default realization (DefaultActionHandler) 
and specific realization(LocalActionHandler, SecureActionHandler, CloudActionHandler). All specific handlers classes 
extends DefaultActionHandler and implements ActionHandler interface.
```java
public class CloudActionHandler extends DefaultActionHandler implements ActionHandler
```


### Security

#### Model

First of all, [Participant](security/src/main/java/com/cloudcastle/security/model/Participant.java) abstract class 
the one who participates in [Transaction](security/src/main/java/com/cloudcastle/security/model/Transaction.java).
Client and Server are subclasses of Participant, and those classes directly represent both sides of the Transaction.

[Client](security/src/main/java/com/cloudcastle/security/model/Client.java) has info about user(username, password).
[Server](security/src/main/java/com/cloudcastle/security/model/Server.java) has one instance, and can create Server instance only by using connect() method. 
```java
Server server = Server.connect();
```
During initialize Server generate pair of private/public key and set name.

#### Integrity

[SignatureService](security/src/main/java/com/cloudcastle/security/integrity/SignatureService.java) the main interface, has two methods - sign() which return signature represented in byte array, 
and verify() witch return boolean true if signature verified, or false otherwise.
```java
public interface SignatureService {
    byte[] sign() throws GeneralSecurityException;
    boolean verify() throws GeneralSecurityException;
}
```

[SignatureHandler](security/src/main/java/com/cloudcastle/security/integrity/SignatureHandler.java) implements SignatureService methods and realize them 
by two classes [Signer](security/src/main/java/com/cloudcastle/security/integrity/Signer.java) and 
[Verifier](security/src/main/java/com/cloudcastle/security/integrity/Verifier.java).

*I split realization sing() and verify() methods and put them into Signer and Verifier classes because this methods work on the opposite sides
of the transaction(sing() on Server side, verify() on Client side)*