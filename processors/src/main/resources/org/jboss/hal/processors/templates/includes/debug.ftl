<inherits name="com.google.gwt.user.Debug"/>
<inherits name="com.google.gwt.debugpanel.DebugPanel_Enabled"/>
<set-property name="gwt.enableDebugId" value="true"/>

<extend-property name="log_level" values="DEBUG"/>
<set-property name="log_level" value="DEBUG"/>
<set-property name="log_ConsoleLogger" value="ENABLED" />
<set-property name="log_GWTLogger" value="ENABLED" />
<set-property name="log_SystemLogger" value="ENABLED" />
<set-property name="log_FirebugLogger" value="ENABLED" />
<set-property name="log_DivLogger" value="DISABLED" />
<set-property name="log_WindowLogger" value="DISABLED" />
<set-configuration-property name="log_pattern" value="%d [%-5p] %m%n" />

<set-property name="compiler.emulatedStack" value="true"/>
<set-configuration-property name="compiler.emulatedStack.recordLineNumbers" value="true"/>
<set-configuration-property name="compiler.emulatedStack.recordFileNames" value="true"/>
