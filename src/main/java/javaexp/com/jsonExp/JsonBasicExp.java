package javaexp.com.jsonExp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonBasicExp {

	public static void main(String[] args) throws Exception {

//		jsonToObj();

//		objectToJson();
	}

	private static void jsonToObj() throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "{\"name\":\"test Name\",\"birthDate\":\"01/01/2011\",\"age\":33,\"messages\":[\"hello jackson 1\",\"hello jackson 2\",\"hello jackson 3\"]}";
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		mapper.setDateFormat(df);
		
		User user = mapper.readValue(jsonInString, User.class);
		
		System.out.println(user.getName());
		System.out.println(user.getBirthDate());
		System.out.println(user.getAge());
		System.out.println(user.getMessages());
	}

	private static void objectToJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);

		User user = createDummyUser();

		try {
			String jsonInString = mapper.writeValueAsString(user);
			System.out.println(jsonInString);

			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			System.out.println(jsonInString);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static User createDummyUser() {

		User user = new User();

		user.setName("test name");
		user.setAge(33);

		List<String> msg = new ArrayList<>();
		msg.add("hello jackson 1");
		msg.add("hello jackson 2");
		msg.add("hello jackson 3");

		user.setMessages(msg);

		return user;

	}
}