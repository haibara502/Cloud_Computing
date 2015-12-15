/*
Try a demo.
*/
import java.io.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerDemo{
	public static void main(String[] args){
		String fieldName = "text";
		String text = "喵尝试一个简单的句子吧喵。";
		
		Analyzer analyzer = new IKAnalyzer();
		
		Directory directory = null;
		IndexWriter iWriter = null;
		IndexSearcher iSearcher = null;
		
		try{
			directory = new RAMDirectory();
			iWriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
			Document doc = new Document();
			doc.add(new Field(fieldName, text, Field.Store.YES, Field.Index.ANALYZED));
			iWriter.addDocument(doc);
			iWriter.close();
			
			iSearcher = new IndexSearcher(directory);
			iSearcher.setSimilarity(new IKSimilarity());
			
			String keyword = "喵";
			
			Query query = IKQueryParser.parse(fieldName, keyword);
			
			TopDocs topDocs = iSearcher.search(query, 5);
			System.out.println("命中：" + topDocs.totalHits);
			
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; ++i){
				Document targetDoc = iSearcher.doc(scoreDocs[i].doc);
				System.out.println("内容：" + targetDoc.toString());
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally{
			if (iSearcher != null)
				try{
					iSearcher.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			if (directory != null)
				try{
					directory.close();
				} catch (IOException e){
					e.printStackTrace();
				}
		}
	}
}
