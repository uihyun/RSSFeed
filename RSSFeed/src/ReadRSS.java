public class ReadRSS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RSSFeedParser parser = new RSSFeedParser("http://tech.uzabase.com/rss");
		Feed feed = parser.readFeed();
		System.out.println(feed.toString());
		FileManager fileManager = FileManager.getInsatnce();
		try {
			fileManager.openWriter();
			fileManager.writeFeedToFile(feed.toString());
			for (FeedMessage message : feed.getMessages()) {
				System.out
						.println(message.toString());
				fileManager.writeFeedToFile(message.toString());
			}
		} finally {
			fileManager.closeWriter();
		}
	}
}
