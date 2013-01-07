//
//  RssXMLParser.m
//  iPhoneRssReader
//
//  Created by James on 12/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "RssXMLParser.h"

@implementation RssXMLParser
@synthesize delegate;

- (void) parseRssXML:(NSData *)xmldata
{
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
        if(articles == nil)
        {
            articles = [[NSMutableDictionary alloc] init];
        }
    }
    else if([elementName isEqualToString:@"title"])
    {
        aNode = title;
    }
    else if([elementName isEqualToString:@"link"])
    {
        aNode = postlink;
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
            if(string.length != 0)
            {
                lastTitle = string;
            }
        }
        
        break;
        case postlink:
        {
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet nonBaseCharacterSet]];
            string = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
            if(string.length != 0 && articles != nil)
            {
                [articles setObject:string forKey:lastTitle];
            }
        }
        break;
            
        default:
            break;
    }
}

@end
