package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		try {
            // create a client object and use it to
            Client client = new Client("sensor", Common.BROKERHOST, Common.BROKERPORT);

            // - connect to the broker - user "sensor" as the user name
            client.connect();

            // - publish the temperature(s)

            for(int i = 0; i < COUNT; i++) {
                double temp = sn.read();

                client.publish(Common.TEMPTOPIC, Double.toString(temp));
                Thread.sleep(1000); // liten pause for å simulere sanntid

            }
            // - disconnect from the broker
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

		System.out.println("Temperature device stopping ... ");

	}
}
