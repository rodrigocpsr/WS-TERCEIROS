package br.com.comven.corews.transacao.util;

public class StringOperations {

	  public static String toUpperCaseSansAccent(String txt) {
	       if (txt == null) {
	          return null;
	       }else{
	    	   return txt.toUpperCase();
	       }
	      
	  }
	  
	  public static String limpaStringEnvio(String txt){
		  
		  String r = StringOperations.toUpperCaseSansAccent(txt);
		  
		  r = r.replace("<", " ");
		  r = r.replace(">", " ");
		  r = r.replace("?", " ");
		  return r;
	  }
	  
	  public static String removeCaracteresAPLIC(String strenvio){
			
			strenvio = strenvio.replaceAll("&", "&amp;");
			return strenvio;
		}
}
