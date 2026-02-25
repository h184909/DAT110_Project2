package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.TODO;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");

        try {
            // create a client object and use it to
            Client client = new Client("display", Common.BROKERHOST, Common.BROKERPORT);

            // - connect to the broker - use "display" as the username
            client.connect();

            // - create the temperature topic on the broker
            client.createTopic(Common.TEMPTOPIC);

            // - subscribe to the topic
            client.subscribe(Common.TEMPTOPIC);

            // - receive messages on the topic
            for (int i = 1; i <= COUNT; i++) {
                Message msg = client.receive();

                if (msg instanceof PublishMsg) {
                    PublishMsg pmsg = (PublishMsg) msg;
                    System.out.println("Temperature received: " + pmsg.getMessage());
                }
            }

            // - unsubscribe from the topic
            client.unsubscribe(Common.TEMPTOPIC);

            // - disconnect from the broker
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		System.out.println("Display stopping ... ");

	}
}
