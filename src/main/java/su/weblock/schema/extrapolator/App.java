package su.weblock.schema.extrapolator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import su.weblock.schema.common.exception.SEBaseException;

public class App {
    public static void main(String[] args) throws SEBaseException, JsonMappingException, JsonProcessingException {        
        var extr = ExtrapolatorMaker.make("application/x-www-form-urlencoded");        
      

        
        extr.pushSample("xxx=1&t=7".getBytes());
        extr.pushSample("xxx=3&y=efw32fde".getBytes());        
        extr.pushSample("xxx=67&t=9".getBytes());        
        extr.pushSample("xxx=1&y=24sdfdf7".getBytes());        
        extr.pushSample("y=uiii&d=22345200-abe8-4f60-90c8-0d43c5f6c0f6".getBytes());        
        extr.pushSample("xxx=8&lol2=24".getBytes());        
        extr.pushSample("xxx=68&lol2=67".getBytes());        
        extr.pushSample("xxx=68&lol3=qwert1".getBytes());        
        extr.pushSample("xxx=68&lol2=qwe3333".getBytes());                

        System.out.println(extr.result().getShortStringDescription());  
        
        System.out.println(" -------------- ");  


        extr = ExtrapolatorMaker.make("multipart/form-data"); 
        String multipart_body1 =
            "----AaB03x\r\n"
            + "Content-Disposition: form-data; name=\"string_param1-name\"\r\n"
            + "\r\n"
            + "Larry\r\n"
            + "----AaB03x\r\n"
            + "Content-Disposition: form-data; name=string_param2-name\r\n"
            + "\r\n"
            + "Larry2222\r\n"
            + "----AaB03x\r\n"            
            + "Content-Disposition: form-data; name=\"files\"; filename=\"file1.txt\"\r\n"
            + "Content-Type: text/plain\r\n"
            + "\r\n"
            + "HELLO WORLD!\r\n"
            + "----AaB03x--\r\n";

        extr.pushSample(multipart_body1.getBytes()); 

        String multipart_body2 =
            "----AaB03x\r\n"
            + "Content-Disposition: form-data; name=\"files\"\r\n"
            + "\r\n"
            + "Larry\r\n"
            + "----AaB03x\r\n"
            + "Content-Disposition: form-data; name=string_param3-name\r\n"
            + "\r\n"
            + "Larry2222\r\n"
            + "----AaB03x\r\n"            
            + "Content-Disposition: form-data; name=\"files1\"; filename=\"file1.txt\"\r\n"
            + "Content-Type: text/plain\r\n"
            + "\r\n"
            + "HELLO WORLD!\r\n"
            + "----AaB03x--\r\n";

        System.out.println(extr.pushSample(multipart_body2.getBytes()).toString());

        extr = ExtrapolatorMaker.make("application/json"); 
        String json1 = "{\"xx\":1}";
        extr.pushSample(json1.getBytes()); 
        String json2 = "{\"yy\":\"qwert\"}";
        extr.pushSample(json2.getBytes());  
        String json3 = "{\"zz\":[1,2,3,4,5]}";
        extr.pushSample(json3.getBytes()); 
        String json4 = "{\"zz\":[1,2,\"lol1\", \"lol2\"]}";
        extr.pushSample(json4.getBytes()); 
        String json5 = "{\"aa\":{\"lol5\": true}}";
        extr.pushSample(json5.getBytes()); 
        String json6 = "{\"aa\":{\"lol6\": \"loadasd\"}}";
        extr.pushSample(json6.getBytes()); 
        String json7 = "{\"zz\":[6, {\"qwer\":\"dddffff\"}, 8]}";
        extr.pushSample(json7.getBytes()); 
        String json8 = "{\"aa\":null}";
        extr.pushSample(json8.getBytes()); 

        var s = extr.result().getShortStringDescription();
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        var tree = om.readTree(s);
        assert(tree.get("xx").asText().equals("INTEGER"));
        assert(tree.get("aa").get("lol5").asText().equals("BOOLEAN"));

        System.out.println(s);

        extr = ExtrapolatorMaker.make("application/json"); 
        String json_1 = "{\"xx\":[]}";
        extr.pushSample(json_1.getBytes());         

        var schema = extr.result().getShortStringDescription();        
        tree = om.readTree(schema);   
        var x = tree.get("xx").iterator().next().asText();    

        System.out.println(x);     
        
        extr = ExtrapolatorMaker.make("application/yaml"); 
        String yaml1 =              
             "ff1: 7.8\n"
            +"in4: 888\n"
            +"ff3: .Inf\n"
            +"ff4: -.Inf\n"
            //+"ff5: -.NAN\n"
            +"ff6: 77.8\n";

        var tt = extr.pushSample(yaml1.getBytes());         
          
        System.out.println(extr.result().getShortStringDescription());
    }
}
