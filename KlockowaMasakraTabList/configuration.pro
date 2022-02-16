# Keep your main class
-keep public class pl.wolny.kwadratowamasakratablist.KwadratowaMasakraTabList {
    public void onEnable();
	public void onDisable();
}

-keep class pl.wolny.kwadratowamasakratablist.model.configuration.TabListConfig

-keep class !pl.wolny.kwadratowamasakratablist.**,!pl.wolny.kwadratowamasakratablist.** { *; }

# Keep event handlers

-keep class * extends org.bukkit.event.Listener {
    @org.bukkit.event.EventHandler <methods>;
}


-libraryjars  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)

-dontshrink
-dontoptimize
-dontwarn
-target 17


# Some attributes that you'll need to keep (to be honest I'm not sure which ones really need to be kept here, but this is what works for me)
-dontusemixedcaseclassnames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod,EventHandler,Override,Serializable
