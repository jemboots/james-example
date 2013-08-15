//
//  RssTableViewController.h
//  iPhoneRssReader
//
//  Created by James on 12/8/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RssXMLParser.h"

@interface RssTableViewController : UITableViewController <NSURLConnectionDataDelegate, XMLParserDelegate>
{
    NSMutableData *httpReceivedData;
    NSMutableArray *articlesList;
}

@end
