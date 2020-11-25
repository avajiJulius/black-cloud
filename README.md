Black Cloud 
=====================
- [Stucure and steps](#structure-and-steps)
- [Modules](#modules)
    - [Black Cloud Desktop Application](#black-cloud-desktop-application)
---------------------

## Structure and Steps

1) [Black Cloud Desktop Application](####black-cloud-desktop-application)
2) Encrypting algorithm
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