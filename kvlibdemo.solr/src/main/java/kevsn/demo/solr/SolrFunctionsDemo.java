/*
 * 2016年4月14日 
 */
package kevsn.demo.solr;

import java.io.IOException;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.Create;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.CreateAlias;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.Delete;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.DeleteAlias;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;

/**
 * @author Kevin
 *
 */
public class SolrFunctionsDemo {

	/**
	 * 
	 */
	private static final String COLLECTION_NAME = "kevin";

	private static String ZK_HOST = "localhost:2181/solrCloud";

	public static void deleteCollection(String collection)
			throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		Delete deleteReq = new CollectionAdminRequest.Delete();
		deleteReq.setCollectionName(collection);
		CollectionAdminResponse resp = deleteReq.process(client);
		System.out.println(resp);
		client.close();
	}

	public static void query() throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		SolrQuery query = new SolrQuery();
		query.set("collection", "kv2");
		String q = "Name:Kevin";
		query.setQuery(q);
		QueryResponse rst = client.query(query);
		System.out.println(rst);
	}

	public static void index(String collection, String name)
			throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("ID", UUID.randomUUID().toString());
		doc.addField("Name", name);
		doc.addField("gender", 0);
		doc.addField("weight", 65 + collection.length());
		doc.addField("height", 170 + collection.length());
		client.add(collection, doc);
		UpdateResponse resp = client.commit(collection);// kevin is the
		System.out.println(resp);

	}

	public static void deleteAlias(String alias)
			throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		DeleteAlias req = new CollectionAdminRequest.DeleteAlias();
		req.setAliasName(alias);
		CollectionAdminResponse resp = req.process(client);
		System.out.println(resp);
		client.close();
	}

	public static void setAlias(String alias, String collection)
			throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		CreateAlias req = new CollectionAdminRequest.CreateAlias();
		req.setAliasName(alias);
		req.setAliasedCollections(collection);
		CollectionAdminResponse resp = req.process(client);
		System.out.println("alias resp:" + resp);
		client.close();
	}

	public static void createCollection(String name)
			throws SolrServerException, IOException {
		CloudSolrClient client = getClient();
		Create createReq = new CollectionAdminRequest.Create();
		createReq.setPath("/admin/collections");
		createReq.setCollectionName(name);
		createReq.setNumShards(1);
		// zookeeper中必须已经存在ais配置，配置路径为/solrCloud/configs/ais
		createReq.setConfigName("ais");
		createReq.setReplicationFactor(1);
		createReq.setMaxShardsPerNode(1);
		CollectionAdminResponse result = createReq.process(client);
		System.out.println("create resp:" + result);
		client.close();
	}

	private static CloudSolrClient getClient() {
		CloudSolrClient client = new CloudSolrClient(ZK_HOST);
		return client;
	}

	public static void main(String[] args)
			throws SolrServerException, IOException {
		// createCollection();
		// setAlias("kv2");
		// index("kevin");
		// setAlias("kv3");
		// index("kv3");
		// query();
		// index("kv3", "Shen");
		// deleteCollection("kevin");
		// createCollection("kevin");
		// createCollection("shen");
		// setAlias("k", "kevin");
		// setAlias("k", "shen");
		
		index("k", "xin");
	}
}
