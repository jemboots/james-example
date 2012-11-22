//
//  PullDownRefreshTableViewController.m
//  PullDownScrollViewComponent
//
//  Created by James on 11/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PullDownRefreshTableViewController.h"
#import <QuartzCore/CALayer.h>

@implementation PullDownRefreshTableViewController
@synthesize exampleData;
@synthesize latestUpdateDate;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];

    exampleData = [NSMutableArray array];
    [exampleData addObject:@"Drag Down to Get More Data"];
    
    
    [self.tableView setDelegate:self];
    
    refreshView = [[UIView alloc] initWithFrame:CGRectMake(0, 0 - REFHEIGHT, 320, REFHEIGHT)];
    refreshLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 320, REFHEIGHT)];
    refreshLabel.textAlignment = UITextAlignmentCenter;
    refreshLabel.text = PULL_DOWN_REFRESH;
    
    refreshArrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"arrow.png"]];
    refreshArrow.frame = CGRectMake(17, 9, 27, 44);
    refreshLoadingIcon = [[UIActivityIndicatorView alloc] init];
    refreshLoadingIcon.frame = CGRectMake(20, 20, 20, 20);
    refreshLoadingIcon.activityIndicatorViewStyle = UIActivityIndicatorViewStyleGray;
    refreshLoadingIcon.hidesWhenStopped = YES;
    
    refreshDateLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 40, 320, 21)];
    refreshDateLabel.textColor = [UIColor darkGrayColor];
    refreshDateLabel.font = [UIFont systemFontOfSize:10];
    refreshDateLabel.textAlignment = UITextAlignmentCenter;
    if (latestUpdateDate != nil) {
        [refreshDateLabel setText:[NSString stringWithFormat:@"%@%@", REFRESH_DATE_PREFIX, latestUpdateDate]];
    }
    
    [refreshView addSubview:refreshLabel];
    [refreshView addSubview:refreshLoadingIcon];
    [refreshView addSubview:refreshDateLabel];
    [refreshView addSubview:refreshArrow];
    [self.tableView addSubview:refreshView];
    
    isLoading = false;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [exampleData count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    NSString *labelStr = (NSString *)[exampleData objectAtIndex:indexPath.row];
    [[cell textLabel] setText:labelStr];
    // Configure the cell...
    
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}

-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    NSLog(@"scroll View Begin Dragging");
    isDraging = true;
}

-(void)scrollViewDidScroll:(UIScrollView *)myscrollView
{
    NSLog(@"scroll view scroll: %f", myscrollView.contentOffset.y);
    
    if(!isLoading)
    {
        if (isDraging && myscrollView.contentOffset.y < 0 - REFHEIGHT)
        {
            [UIView animateWithDuration:0.25 animations:^{
                refreshLabel.text = RELEASE_REFRESH;
                CALayer *layer = refreshArrow.layer;
                layer.transform = CATransform3DMakeRotation(M_PI, 0, 0, 1);
            }];
        }
        else
        {
            [UIView animateWithDuration:0.25 animations:^{
                refreshLabel.text = PULL_DOWN_REFRESH;
                [refreshArrow layer].transform = CATransform3DMakeRotation(M_PI * 2, 0, 0, 1);
            }];
        }
    }
}

-(void)scrollViewDidEndDragging:(UIScrollView *)myscrollView willDecelerate:(BOOL)decelerate
{
    isDraging = false;
    if (myscrollView.contentOffset.y < 0 - REFHEIGHT)
    {
        
        [UIView animateWithDuration:0.5 animations:^{
            self.tableView.contentInset = UIEdgeInsetsMake(REFHEIGHT, 0, 0, 0);
        }];
        
        refreshLabel.text = LOADING;
        isLoading = true;
        
        [refreshLoadingIcon startAnimating];
        [refreshArrow setHidden:YES];
        
        [self performSelector:@selector(stopLoading) withObject:nil afterDelay:2.0];
    }
}

- (void)stopLoading
{
    isLoading = false;
    [refreshLoadingIcon stopAnimating];
    [refreshArrow setHidden:NO];
    
    // Hide the header
    [UIView animateWithDuration:0.3 animations:^{
        self.tableView.contentInset = UIEdgeInsetsZero;
    }
                     completion:^(BOOL finished) {
                         [self performSelector:@selector(stopLoadingComplete)];
                     }];
}

- (void)stopLoadingComplete
{
    // Reset the header
    refreshLabel.text = PULL_DOWN_REFRESH;
    
    NSDate *now = [[NSDate alloc] init];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = @"dd/MM/yy HH:mm"; 
    latestUpdateDate = [formatter stringFromDate:now];
    [refreshDateLabel setText:[NSString stringWithFormat:@"%@%@", REFRESH_DATE_PREFIX, latestUpdateDate]];
    
    NSString *newItem = [NSString stringWithFormat:@"New Item at %@", latestUpdateDate];
    [exampleData addObject:newItem];
    [self.tableView reloadData];
}
@end
