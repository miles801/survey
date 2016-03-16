package ${clazz.packagename};  
   
 <#list clazz.imports as being>  
 import ${being};  
 </#list>  
   
 public class ${clazz.classname} {  
   
 <#list attributes as being>  
    private ${being.type} ${being.name};  
      
    public ${being.type} get${being.name?cap_first}() {  
        return ${being.name};  
    }  
      
    public void set${being.name?cap_first}(${being.type} ${being.name}) {  
        this.${being.name} = ${being.name};  
    }  
 </#list>  