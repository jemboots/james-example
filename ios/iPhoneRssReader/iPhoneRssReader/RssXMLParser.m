//
//  RssXMLParser.m
//  iPhoneRssReader
//
//  Created by James on 12/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "RssXMLParser.h"
#import "RssData.h"

@implementation RssXMLParser
@synthesize delegate;

- (void) parseRssXML:(NSData *)xmldata
{
    articles = [[NSMutableArray alloc] init];
    NSXMLParser *xmlParser = [[NSXMLParser alloc] initWithData:xmldata];
    [xmlParser setDelegate:self];
    [xmlParser setShouldResolveExternalEntities:NO];
    [xmlParser parse];
}

- (void) parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    if([elementName isEqualToString:@"item"])
    {
        aNode = invalidNode;
        RssData *rssdata = [[RssData alloc] init];
        [articles addObject:rssdata];
    }
    else if([elementName isEqualToString:@"title"])
    {
        aNode = title;
    }
    else if([elementName isEqualToString:@"link"])
    {
        aNode = postlink;
    }
    else if([elementName isEqualToString:@"content:encoded"])
    {
        aNode = content;
    }
    else if([elementName isEqualToString:@"pubDate"])
    {
        aNode = pubDate;
    }
    else
    {
        aNode = invalidNode;
    }
}


- (void) parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if( [elementName isEqualToString:@"rss"] )
    {
        [delegate onParserComplete:articles XMLParser:self];
    }
}


- (void) parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
{
    switch (aNode) {
        case title:
        {
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet nonBaseCharacterSet]];
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
            if([string length] != 0)
            {
                RssData *rss = [articles lastObject];
                if( rss.title )
                {
                    [rss setTitle:[NSString stringWithFormat:@"%@%@", rss.title, string]]; 
                }
                else
                {
                    [rss setTitle:string]; 
                }
            }
        }
        
        break;
        case postlink:
        {
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet nonBaseCharacterSet]];
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
            if([string length] != 0)
            {
                RssData *rss = [articles lastObject];
                if( rss.link )
                {
                    [rss setLink:[NSString stringWithFormat:@"%@%@", rss.link, string]]; 
                }
                else
                {
                    [rss setLink:string]; 
                }
            }
        }
        break;
        case pubDate:
        {
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet nonBaseCharacterSet]];
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
            if([string length] != 0)
            {
                RssData *rss = [articles lastObject];
                if( rss.publishDate )
                {
                    [rss setPublishDate:[NSString stringWithFormat:@"%@%@", rss.publishDate, string]]; 
                }
                else
                {
                    [rss setPublishDate:string]; 
                }
            }
        }
            break;
        default:
            break;
    }
}

-(void) parser:(NSXMLParser *)parser foundCDATA:(NSData *)CDATABlock
{
    if (aNode == content)
    {
        RssData *rss = [articles lastObject];
        rss.content = [[NSString alloc] initWithData:CDATABlock encoding:NSUTF8StringEncoding];
    }
}

@end
