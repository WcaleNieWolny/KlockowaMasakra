# Keep your main class
-keep public class pl.wolny.kwadratowamasakratablist.KwadratowaMasakraTabList {
    public void onEnable();
	public void onDisable();
}

# Keep event handlers
-keep,allowobfuscation class * extends org.bukkit.event.Listener {
    @org.bukkit.event.EventHandler <methods>;
}

# If your goal is obfuscating and making things harder to read, repackage your classes with this rule
-repackageclasses 'my.package'

# Some attributes that you'll need to keep (to be honest I'm not sure which ones really need to be kept here, but this is what works for me)
-keepattributes !LocalVariableTable,!LocalVariableTypeTable,Exceptions,InnerClasses,Signature,Deprecated,LineNumberTable,*Annotation*,EnclosingMethod