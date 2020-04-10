/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiki;

/**
 *
 * @author BintangDiLangit
 */
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Collection;
import java.util.List;
import java.io.IOException;
import java.util.Collections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

// Deep first search
public class iterativeDFS {

    /* parameter root adalah root dari tree yang ingin dilintasi(traverse),
    maka kita memulai dengan membuat stack 
     */
    private static void iterativeDFS(Node root) {
        /* pendeklarasian object Deque yang hanya bisa diisi oleh Node,
           semua fungsi di dequenode itu disimpan pada variabel stack yang berbentuk ArrayDeque
         */
        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root); // dan memasukkan/mempush root

        /* jika stack tidak kosong, maka jalankan atau
           looping akan terus berlanjut sampai stack kosong
         */
        while (!stack.isEmpty()) {

            // dan stack akan ter pop atau berkurang sampai stack kosong
            Node node = stack.pop();

            // jika mendapat textNode nya, 
            if (node instanceof TextNode) {
                System.out.print(node); // dia akan memprint node/content tersebut
            }
            /*
            pendeklarasian object List yang hanya bisa diisi oleh Node,
            semua fungsi di listnode itu disimpan pada variabel nodes
             */
            // memanfaatkan arrayList yang hanya diisi bentuk Node
            List<Node> nodes = new ArrayList<Node>(node.childNodes()); // dengan mengambil variable node dijadikan childnya
            Collections.reverse(nodes); // lalu membalik urutan elemen dari daftar yang diteruskan sebagai argumen

            for (Node child : nodes) { // Untuk setiap child dalam nodes, maka lakukan perulangan
                stack.push(child);// kemudian akan mempush children ke stack
            }
        }
    }

    public static void main(String[] args) throws IOException {

        // model 1
        // prepare the address to be taken (source)
        // mempersiapkan url nya
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        // prepare a connection to the destination url
        // mengkoneksikan ke url tujuan
        Connection con = Jsoup.connect(url);
        // get (source) from the destination address
        // mendapat source dari alamat tujuan
        Document doc = con.get();
        // selecting the source obtained (based on the id mw-content-text)
        // menyeleksi source yang didapat berdasarakan: mw-content-text
        Element content = doc.getElementById("mw-content-text");
        /* select the content text and pull out the paragraphs
        pilih teks konten dan tarik atau mendapatkan paragraf */
        Elements paras = content.select("p");
        // get data in array 1
        Element firstPara = paras.get(1);
        // display data with iteratiion(iterasi) mode
        iterativeDFS(firstPara);
        System.out.println();

        // model 2 (hanya diperingkas)
//        Document d = Jsoup.connect("https://en.wikipedia.org/wiki/Java_(programming_language)").get();
//        String title = d.title();
//        System.out.println("title: "+title);
//        
//        Elements links = d.select("p");
//        Element firstPara = links.get(1);
//        iterativeDFS(firstPara);
//        System.out.println();
//        
//        for (Element link:links) {
//            System.out.println("\nlink: "+link.attr("a[href]"));
//            System.out.println("text: "+link.text());
//        }
    }
}
