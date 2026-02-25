package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

        ClientSession session = new ClientSession(user, connection);
        clients.put(user, session);
        Logger.log("Storage: added client session for user=" + user);

		
	}

	public void removeClientSession(String user) {

        //tar ut sesjonen viss den finst
		ClientSession session = clients.remove(user);

        if (session != null) {
            session.disconnect();
        }

		Logger.log("Storage: removed client session for user=" + user);
		
	}

	public void createTopic(String topic) {

        //oppretter en tom subscriber-mengde for topic hvis den ikke finnes fra før
        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
        Logger.log("Storage: created topic=" + topic);
	
	}

	public void deleteTopic(String topic) {

        subscriptions.remove(topic);
        Logger.log("Storage: deleted topic=" + topic);
		
	}

	public void addSubscriber(String user, String topic) {

        //legger først inn topic, så henter den ut igjen for å sette inn bruker
        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
        subscriptions.get(topic).add(user);
        Logger.log("Storage: user=" + user + " subscribed to topic=" + topic);
		
	}

	public void removeSubscriber(String user, String topic) {

		Set<String> subs = subscriptions.get(topic);
        if (subs != null) {
            subs.remove(user);
        }
        Logger.log("Storage: user=" + user + " unsubscribed from topic=" + topic);
	}
}
