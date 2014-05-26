using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using PayPalExpressCheckoutLib;

namespace Example_Site.Controllers
{
    public class HomeController : Controller
    {
        public string API_UserName = "pygy_1283-developer_api1.hotmail.com";
        public string API_Password = "1400646170";
        public string API_Signature = "A20jaHW9k9lZQvdq8GMEdfqSA5U3AViti73WjAUiysH4a4TpBiaaxQiK";
        public bool isTestingMode = true;

        public string returnURL = "http://localhost:60688/Home/ReturnPage";
        public string cancelURL = "http://localhost:60688/Home/CancelPage";
        public string paymentType = "Sale";
        public string currencyCodeType = "USD";

        public string itemCategory = "Digital"; //Physical or Digital

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Checkout()
        {
            PayPalExpressCheckout pp = new PayPalExpressCheckout(API_UserName, API_Password, API_Signature, isTestingMode);

            List<PayPalExpressCheckout.PayPalItem> items = new List<PayPalExpressCheckout.PayPalItem>();
            PayPalExpressCheckout.PayPalItem item = new PayPalExpressCheckout.PayPalItem();
            item.name = "My Digital Product 1";
            item.amt = "0.99";
            item.qty = "1";
            items.Add(item);

            //add more for shopping cart
            item = new PayPalExpressCheckout.PayPalItem();
            item.name = "Product 2";
            item.amt = "1.99";
            item.qty = "1";
            items.Add(item);

            float totalAmount = 0;
            foreach (PayPalExpressCheckout.PayPalItem i in items)
            {
                totalAmount += float.Parse(i.amt);
            }

            string paymentAmount = totalAmount.ToString();
            string customField = "trackcode=1&id=2"; //for your self tracking

            NameValueCollection nvpResArray = pp.SetExpressCheckoutDG(paymentAmount, currencyCodeType, paymentType, returnURL, cancelURL, items, customField, true, itemCategory);
            string ack = nvpResArray.GetValues("ACK").First().ToUpper();
            if (ack == "SUCCESS" || ack == "SUCCESSWITHWARNING")
            {
                string token = nvpResArray.GetValues("TOKEN").First();
                //redirect to paypal
                if(itemCategory == "Digital")
                {
                    Response.Redirect(pp.PAYPAL_DG_URL + token);
                }
                else
                {
                    Response.Redirect(pp.PAYPAL_URL + token);
                }
            }
            else
            {
                //failed
                ViewBag.result = nvpResArray;
                ViewBag.status = "paypal failed";
            }

            return View();
        }

        public ActionResult ReturnPage()
        {
            string token = Request["token"];
            string payerID = Request["PayerID"];

            PayPalExpressCheckout pp = new PayPalExpressCheckout(API_UserName, API_Password, API_Signature, isTestingMode);
            //get payment info
            NameValueCollection res = pp.GetExpressCheckoutDetails(token);
            string ack = res.GetValues("ACK").First().ToUpper();
            if (ack != "SUCCESS" && ack != "SUCCESSWITHWARNING")
            {
                ViewBag.status = "paypal failed";
                ViewBag.result = res;
                return View("ReturnPage");
            }

            string finalPaymentAmount = res.GetValues("PAYMENTREQUEST_0_AMT").First();
            string paymentType = "Sale";
            string currencyCodeType = res.GetValues("CURRENCYCODE").First();
            string customField = res.GetValues("PAYMENTREQUEST_0_CUSTOM").First();
            string payerEmail = res.GetValues("EMAIL").First();
            string payerFirstName = res.GetValues("FIRSTNAME").First();
            string payerLastName = res.GetValues("LASTNAME").First();
            string country = res.GetValues("COUNTRYCODE").First();

            List<PayPalExpressCheckout.PayPalItem> items = new List<PayPalExpressCheckout.PayPalItem>();
            List<string> itemsNameList = new List<string>();
            int itemNumber = 0;

            while (res.GetValues("L_PAYMENTREQUEST_0_NAME" + itemNumber.ToString()) != null)
            {
                PayPalExpressCheckout.PayPalItem item = new PayPalExpressCheckout.PayPalItem();
                item.name = res.GetValues("L_PAYMENTREQUEST_0_NAME" + itemNumber.ToString()).First();
                item.amt = res.GetValues("L_PAYMENTREQUEST_0_AMT" + itemNumber.ToString()).First();
                item.qty = res.GetValues("L_PAYMENTREQUEST_0_QTY" + itemNumber.ToString()).First();
                items.Add(item);
                itemsNameList.Add(item.name);
                itemNumber++;
            }

            NameValueCollection result = pp.ConfirmPayment(token, paymentType, currencyCodeType, payerID, finalPaymentAmount, items, customField, Request.ServerVariables["SERVER_NAME"], itemCategory);
            ack = result.GetValues("ACK").First().ToUpper();
            if (ack == "SUCCESS" || ack == "SUCCESSWITHWARNING")
            {
                ViewBag.status = "successfully";
                string transactionId = result.GetValues("PAYMENTINFO_0_TRANSACTIONID").First();
                string currency = result.GetValues("PAYMENTINFO_0_CURRENCYCODE").First();
                string orderTime = result.GetValues("PAYMENTINFO_0_ORDERTIME").First();
                string amt = result.GetValues("PAYMENTINFO_0_AMT").First();
                string feeAmt = result.GetValues("PAYMENTINFO_0_FEEAMT").First();
                string taxAmt = result.GetValues("PAYMENTINFO_0_TAXAMT").First();
                string paymentStatus = result.GetValues("PAYMENTINFO_0_PAYMENTSTATUS").First();
                string pendingReason = result.GetValues("PAYMENTINFO_0_PENDINGREASON").First();

                ViewBag.transactionId = transactionId;
                ViewBag.amt = amt;
                ViewBag.currency = currency;
            }

            return View();
        }

        public ActionResult CancelPage()
        {
            return View();
        }
    }
}
