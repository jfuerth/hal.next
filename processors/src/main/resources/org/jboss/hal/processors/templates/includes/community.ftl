<!-- Include once GWTP dependency is removed from Ballroom -->
<!--<inherits name="org.jboss.ballroom.Framework"/>-->

<!-- This needs to be redeclared _after_ inheriting the ballroom module -->
<replace-with class="org.jboss.as.console.client.ConsoleFramework">
    <when-type-is class="org.jboss.ballroom.client.spi.Framework"/>
</replace-with>

<set-configuration-property name="console.profile" value="community"/>
