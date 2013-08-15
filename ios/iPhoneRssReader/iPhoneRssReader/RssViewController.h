//
//  RssViewController.h
//  iPhoneRssReader
//
//  Created by James on 8/15/13.
//  Copyright (c) 2013 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RssData.h"

#define HTML_HEADER     @"<html><head><style>h1 {font-size: 1.25em;} h2, h3, h4, h5, h6 {font-size: 1em;} body {font-family:\"arial\";}img {max-width: 310px;} blockquote {color: #999; border-left: solid 1px #ECECEC; padding-left: 15px; margin: 0;} </style><script type=\"text/javascript\">function setAllImgSize(){var imgs = document.getElementsByTagName(\"img\");for (var i = 0; i < imgs.length; i++){imgs[i].style.height = \"auto\";}}</script></head><body onload=\"setAllImgSize()\">"
#define HTML_FOOTER     @"</body></html>"

@interface RssViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIWebView *rssWebView;
@property (retain, nonatomic) RssData *rssData;
@end
