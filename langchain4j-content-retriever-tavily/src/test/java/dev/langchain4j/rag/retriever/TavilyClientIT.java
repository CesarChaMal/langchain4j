package dev.langchain4j.rag.retriever;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.System.getenv;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;

class TavilyClientIT {

    public static final int maxResults = 5;

    @Test
    void should_search_google() {
        //given
        TavilyClient tavilyClient = TavilyClient.builder()
                .baseUrl("https://api.tavily.com")
                .timeout(ofSeconds(60))
                .build();

        //when
        TavilyResponse search = tavilyClient.search(
                TavilySearchRequest.builder()
                        .apiKey(getenv("TAVILY_API_KEY"))
                        .query("What are the best shopping centers in Munich?")
                        .searchDepth(SearchDepth.BASIC)
                        .maxResults(maxResults)
                        .includeAnswer(false)
                        .includeImages(true)
                        .includeRawContent(true)
                        .includeDomains(new ArrayList<>())
                        .excludeDomains(new ArrayList<>()).build()
        );

        //then
       assertThat(search.getResults().size()).isLessThanOrEqualTo(maxResults);

        try {
            search.getResults().forEach(tavilySearchResult ->
                    System.out.println("Answer: URL - " + tavilySearchResult.getUrl() +" , Content - "+ tavilySearchResult.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}