import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser {
	private static final String ITEM = "item";
	private static final String TITLE = "title";
	private static final String LINK = "link";
	private static final String DESCRIPTION = "description";
	private static final String LAST_BUILD_DATE = "lastBuildDate";
	private static final String PUB_DATE = "pubDate";
	private static final String GUID = "guid";

	private final URL url;

	public RSSFeedParser(String rssUrl) {
		try {
			this.url = new URL(rssUrl);
		} catch (MalformedURLException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public Feed readFeed() {
		Feed feed = null;
		try {
			boolean isFeedHeader = true;
			String title = "";
			String link = "";
			String description = "";
			String lastBuildDate = "";
			String pubdate = "";
			String guid = "";
			StringBuilder temp = null;
			boolean isDescription = false;

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(excludeWord(title), link,
									excludeWord(description), lastBuildDate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						temp = new StringBuilder(description);
						isDescription = true;
						break;
					case LAST_BUILD_DATE:
						lastBuildDate = getCharacterData(event, eventReader);
						break;
					case PUB_DATE:
						pubdate = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart()
							.equals(DESCRIPTION)) {
						description = temp.toString();
						isDescription = false;
					} else if (event.asEndElement().getName().getLocalPart()
							.equals(ITEM)) {
						FeedMessage message = new FeedMessage(
								excludeWord(title), link,
								excludeWord(description), pubdate, guid);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				} else if (isDescription) {
					if (event instanceof Characters) {
						temp.append(event.asCharacters().getData());
					}
				}
			}
		} catch (XMLStreamException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public String excludeWord(String text) {
		String excludedWord = "NewsPicks";
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (int j = 0, n = text.length(), m = excludedWord.length(); j < n;) {
			int index = text.indexOf(excludedWord, j);
			if (index == -1)
				break;
			if (index > 0)
				sb.append(text.substring(j, index));
			j = index + m;
			i = j;
		}
		sb.append(text.substring(i));
		return sb.toString();
	}
}
