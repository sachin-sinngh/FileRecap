import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
public class Trie {

    Trie nodes[];
    int occurrence;
    boolean isEndofWord;
    static int MAX_SIZE=(int)Math.pow(2,20);
	static String word[]=new String[MAX_SIZE];
	static int occ[]=new int[MAX_SIZE];
	static int count=0;

    public Trie(){
        this.nodes=new Trie[26];
        this.occurrence=0;
        for(int i=0;i<26;i++)
            this.nodes[i]=null;
        this.isEndofWord=false;
    }

    void insert(String key){
        Trie temp=this;
        int length=key.length();
        for(int i=0;i<length;i++){
			char c=key.charAt(i);
			if(!Character.isLetter(c))
			    return;
			int index=(int)Character.toLowerCase(c)-97;
			if(temp.nodes[index]==null)
				temp.nodes[index]=new Trie();
			temp=temp.nodes[index];
		}
           temp.isEndofWord=true;
           temp.occurrence++;
    }
    int search(String key){
           Trie temp=this;
           int length=key.length();
           for(int i=0;i<length;i++) {
               char c = key.charAt(i);
               if(!Character.isLetter(c))
                   return 0;
               int index=(int)Character.toLowerCase(c)-97;
               if (temp.nodes[index] == null)
					return 0;
               temp = temp.nodes[index];
			}
           if(temp.isEndofWord)
				return temp.occurrence;
           return 0;
    }
		/*boolean wordbreak(String key){
			int length=key.length();
			if(length==0){
				System.out.println();
				return true;
			}
			for(int i=1;i<=length;i++){
				if(this.search(key.substring(0,i)) && this.wordbreak(key.substring(i,length))){
					return true;
				}
			}
			return false;
		}*/
		static void printAll(Trie temp,char[] arr,int k){
			if(temp==null)
				return;
			if(temp.isEndofWord){
				String s="";
				for(int j=0;j<k;j++)
					s+=arr[j];
             //   System.out.print(s);
				//System.out.println(": "+temp.occurrence);
				word[count]=s;
				occ[count]=temp.occurrence;
				count++;
                return;
			}
			for(int i=0;i<26;i++){
				if(temp.nodes[i]!=null){
					arr[k]=(char)(i+97);
					printAll(temp.nodes[i],arr,k+1);
				}	
			}
		}
        static String rankSentences(String[] sentences,int top_words,int top_sentences){
            int ranks[][]=new int[sentences.length][2];
            for(int i=0;i<sentences.length;i++){
                ranks[i][0]=i;
                ranks[i][1]=0;
                for(int j=0;j<top_words;j++){
                    if(sentences[i].contains(word[j]))
                        ranks[i][1]++;
                }
            }
            for(int i=0;i<ranks.length;i++)
                for(int j=0;j<ranks.length-i-1;j++){
                if(ranks[j][1]<ranks[j+1][1]){
                    int temp=ranks[j][1];
                    ranks[j][1]=ranks[j+1][1];
                    ranks[j+1][1]=temp;
                    temp=ranks[j][0];
                    ranks[j][0]=ranks[j+1][0];
                    ranks[j+1][0]=temp;
                    }
                }
            for(int i=0;i<top_sentences;i++){
                    for(int j=0;j<top_sentences-i-1;j++){
                        if(ranks[j][0]>ranks[j+1][0]){
                            int temp=ranks[j][1];
                            ranks[j][1]=ranks[j+1][1];
                            ranks[j+1][1]=temp;
                            temp=ranks[j][0];
                            ranks[j][0]=ranks[j+1][0];
                            ranks[j+1][0]=temp;
                        }
                    }
            }
            String summary="";
            for(int i=0;i<top_sentences;i++)
                summary+=sentences[ranks[i][0]]+".\n";
            return summary;
        }

		public String getSummary(File infile,int top_sentences) throws IOException{
		    //reading file
            String paragraph=new String(Files.readAllBytes(Paths.get(infile.getAbsolutePath())));
			Trie root=new Trie();
			int top_words=50;
            paragraph = paragraph.replace(System.getProperty("line.separator"), "");
            System.out.print(paragraph);
			//splitting to sentences
            String[] sentences=paragraph.split("\\.");
            //inserting into trie
            for(String sentence: sentences){
                String w[]=sentence.split(" ");
                for(String word: w)
                    root.insert(word);
            }
            root.isEndofWord=false;
			printAll(root,new char[MAX_SIZE],0);
			//sorting
			for(int i=0;i<count;i++)
				for(int j=0;j<count-i-1;j++)
					if(occ[j+1]>occ[j]){
						int temp=occ[j];
						occ[j]=occ[j+1];
						occ[j+1]=temp;
						String s=word[j];
						word[j]=word[j+1];
						word[j+1]=s;
					}
            return rankSentences(sentences,top_words,top_sentences);
		}
}