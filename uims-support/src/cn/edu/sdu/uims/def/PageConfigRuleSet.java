package cn.edu.sdu.uims.def;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PageConfigRuleSet extends RuleSetBase{
	public  UPageConfigModule module ;
	 public void addRuleInstances(Digester digester) {

	        digester.addObjectCreate
	            ("pagepaneltemplate/page",
	             "cn.edu.sdu.uims.def.UPageTemplate",
	             "className");
	        digester.addSetProperties
	            ("pagepaneltemplate/page");
	        digester.addSetNext
	            ("pagepaneltemplate/page",
	             "addPageTemplateConfig",
	             "cn.edu.sdu.uims.def.UPageTemplate");

	        digester.addObjectCreate
            ("pagepaneltemplate/page/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/component");
	        digester.addCallMethod("pagepaneltemplate/page/component/option",
	    	        "addOptions", 0 );
//	        digester.addCallMethod("pagepaneltemplate/page/component/component",
//	    	        "addComponentLabel", 3 );
//	        digester.addCallParam("pagepaneltemplate/page/component/component/type", 0);
//	        digester.addCallParam("pagepaneltemplate/page/component/component/text", 1);
//	        digester.addCallParam("pagepaneltemplate/page/component/component/colSpan", 2);
	        digester.addObjectCreate
            ("pagepaneltemplate/page/component/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/component/component");
	        digester.addSetNext
            ("pagepaneltemplate/page/component/component",
             "addComponentLabel",
             "cn.edu.sdu.uims.def.UPageElementTemplate");
	        
	        digester.addSetNext
            ("pagepaneltemplate/page/component",
             "addComponentTemplateConfig",
             "cn.edu.sdu.uims.def.UPageElementTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/component",
             new GetPageRefrenceRule());

	        digester.addObjectCreate
            ("pagepaneltemplate/page/component/event",
             "cn.edu.sdu.uims.def.UPageEventTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/component/event");
	        digester.addSetNext
            ("pagepaneltemplate/page/component/event",
             "addPageEventTemplateConfig",
             "cn.edu.sdu.uims.def.UPageEventTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/component/event",
             new SetPageEventRule());
	        
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable",
             "cn.edu.sdu.uims.def.UPageTableTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable",
             "addComponentTemplateConfig",
             "cn.edu.sdu.uims.def.UPageTableTemplate");
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow",
             "cn.edu.sdu.uims.def.UPageRowTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow",
             "addPageRowTemplateConfig",
             "cn.edu.sdu.uims.def.UPageRowTemplate");
	        
	        
	        //xrow/group
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/group",
             "cn.edu.sdu.uims.def.UPageGroupTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/group");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/group",
             "addPageElementTemplateConfig",
             "cn.edu.sdu.uims.def.UPageGroupTemplate");
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/group/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/group/component");
	        digester.addCallMethod("pagepaneltemplate/page/xtable/xrow/group/component/option",
	        "addOptions", 0 );
//	        digester.addCallMethod("pagepaneltemplate/page/xtable/xrow/group/component/component",
//	    	        "addComponentLabel", 3 );
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/group/component/component/type", 0);
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/group/component/component/text", 1);
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/group/component/component/colSpan", 2);        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/group/component/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/group/component/component");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/group/component/component",
             "addComponentLabel",
             "cn.edu.sdu.uims.def.UPageElementTemplate");
	        
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/group/component",
             "addComponentToGroup",
             "cn.edu.sdu.uims.def.UPageElementTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/xtable/xrow/group/component",
             new SetGroupComponentToAncestorRule());
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/group/component/event",
             "cn.edu.sdu.uims.def.UPageEventTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/group/component/event");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/group/component/event",
             "addPageEventTemplateConfig",
             "cn.edu.sdu.uims.def.UPageEventTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/xtable/xrow/group/component/event",
             new SetPageEventRule());
	        
	        //xrow/component
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/component");
	        digester.addCallMethod("pagepaneltemplate/page/xtable/xrow/component/option",
	        "addOptions", 0 );
	        
//	        digester.addCallMethod("pagepaneltemplate/page/xtable/xrow/component/component",
//	    	        "addComponentLabel", 3 );
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/component/component/type", 0);
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/component/component/text", 1);
//	        digester.addCallParam("pagepaneltemplate/page/xtable/xrow/component/component/colSpan", 2);
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/component/component",
             "cn.edu.sdu.uims.def.UPageElementTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/component/component");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/component/component",
             "addComponentLabel",
             "cn.edu.sdu.uims.def.UPageElementTemplate");
	        
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/component",
             "addPageElementTemplateConfig",
             "cn.edu.sdu.uims.def.UBaseTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/xtable/xrow/component",
             new SetComponentToAncestorRule());
	        
	        digester.addObjectCreate
            ("pagepaneltemplate/page/xtable/xrow/component/event",
             "cn.edu.sdu.uims.def.UPageEventTemplate",
             "className");
	        digester.addSetProperties
            ("pagepaneltemplate/page/xtable/xrow/component/event");
	        digester.addSetNext
            ("pagepaneltemplate/page/xtable/xrow/component/event",
             "addPageEventTemplateConfig",
             "cn.edu.sdu.uims.def.UPageEventTemplate");
	        digester.addRule
            ("pagepaneltemplate/page/xtable/xrow/component/event",
             new SetPageEventRule());
	   }
	 
	 final class GetPageRefrenceRule extends Rule {

		    public GetPageRefrenceRule() {
		        super();
		    }
		    public void begin(String namespace, String name, Attributes attributes) throws Exception {
		    	UPageElementTemplate et = (UPageElementTemplate) digester.peek();
		    	UPageTemplate pt = (UPageTemplate)digester.peek(1);
		    	et.setPt(pt);
		    }

		}
	 final class SetPageEventRule extends Rule {

		    public SetPageEventRule() {
		        super();
		    }
		    public void begin(String namespace, String name, Attributes attributes) throws Exception {
		        String vars = attributes.getValue("vars");
		        if (vars != null) {
		        	UPageEventTemplate mc = (UPageEventTemplate) digester.peek();
		            String depends[] = vars.split(",");
		            for(int i=0;i<depends.length;i++){
		            	mc.depends.add(depends[i]);
		            }
		        }
		    }

		}
	 final class SetComponentToAncestorRule extends Rule {

		    public SetComponentToAncestorRule() {
		        super();
		    }
		    public void begin(String namespace, String name, Attributes attributes) throws Exception {
		    	UPageElementTemplate et = (UPageElementTemplate) digester.peek();
		        UPageTableTemplate tt = (UPageTableTemplate)digester.peek(2);
		        UPageTemplate pt = (UPageTemplate)digester.peek(3);
		        tt.addElementToTable(et);
		        pt.addPageElement(et);
		        et.setPt(pt);
		    }

	 }
	 
	 final class SetGroupComponentToAncestorRule extends Rule {

		    public SetGroupComponentToAncestorRule() {
		        super();
		    }
		    public void begin(String namespace, String name, Attributes attributes) throws Exception {
		    	UPageElementTemplate et = (UPageElementTemplate) digester.peek();
		        UPageTableTemplate tt = (UPageTableTemplate)digester.peek(3);
		        UPageTemplate pt = (UPageTemplate)digester.peek(4);
		        tt.addElementToTable(et);
		        pt.addPageElement(et);
		        et.setPt(pt);
		    }

	 }
	 
	 public static void main(String args[])throws Exception{
		 PageConfigRuleSet conf = new PageConfigRuleSet();
		 Digester digester = conf.initConfigDigester();
		 conf.module = new UPageConfigModule("recruit");
		 digester.push(conf.module);
         String path = "f:/recruit-configure.xml";
         conf.parseModuleConfigFile(digester, path);
         System.out.println();
	 }
	 protected Digester initConfigDigester() throws ServletException {

	        // Create a new Digester instance with standard capabilities
		 	Digester configDigester = new Digester();
	        configDigester.setNamespaceAware(true);
	        configDigester.setValidating(false);
	        configDigester.setUseContextClassLoader(true);
	        configDigester.addRuleSet(this);
	        // Return the completely configured Digester instance
	        return (configDigester);
	  }
	 
	  protected void parseModuleConfigFile(Digester digester, String path)
      throws UnavailableException {
	InputStream input = null;
      try {
    	  input = new FileInputStream(new File(path));
          InputSource is = new InputSource(input);
          is.setByteStream(input);
          digester.parse(is);

      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
    	  e.printStackTrace();
      } catch (SAXException e) {
    	  e.printStackTrace();
      } finally {
          if (input != null) {
              try {
                  input.close();
              } catch (IOException e) {
                  throw new UnavailableException(e.getMessage());
              }
          }
      }
  }
}
