using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Collections.Specialized;
using System.Web;
using System.Net;

namespace PayPalExpressCheckoutLib
{
    public class PayPalExpressCheckout
    {
        public string API_Endpoint { get; set; }
        public string PAYPAL_URL { get; set; }
        public string PAYPAL_DG_URL { get; set; }

        public string API_UserName { get; set; }
        public string API_Password { get; set; }
        public string API_Signature { get; set; }


        public string sBNCode = "PP-ECWizard";
        public bool SandboxFlag = true;
        public bool USE_PROXY = false;
        public string version = "84";
        public string token = null;

        public PayPalExpressCheckout(string api_user, string api_password, string api_sign, bool isUsingSandbox)
        {
            API_UserName = api_user;
            API_Password = api_password;
            API_Signature = api_sign;
            SandboxFlag = isUsingSandbox;

            if(SandboxFlag)
            {
                API_Endpoint = "https://api-3t.sandbox.paypal.com/nvp";
                PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
                PAYPAL_DG_URL = "https://www.sandbox.paypal.com/incontext?token=";
            }
            else
            {
		        API_Endpoint = "https://api-3t.paypal.com/nvp";
		        PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		        PAYPAL_DG_URL = "https://www.paypal.com/incontext?token=";
            }
        }

        public class PayPalItem
        {
            public string name {get; set;}
            public string amt {get; set;}
            public string qty {get;set;}
        }

        public NameValueCollection SetExpressCheckoutDG(string paymentAmount, string currencyCodeType, string paymentType, string returnURL, string cancelURL, List<PayPalItem> items, string customField, bool payByCreditCard, string itemCategory)
        {
            HttpServerUtility server = HttpContext.Current.Server;
            NameValueCollection nvpstr = new NameValueCollection();
            nvpstr["PAYMENTREQUEST_0_AMT"] = server.UrlEncode(paymentAmount);
            nvpstr["PAYMENTREQUEST_0_PAYMENTACTION"] = server.UrlEncode(paymentType);
            nvpstr["RETURNURL"] = returnURL;
            nvpstr["CANCELURL"] = cancelURL;
            nvpstr["PAYMENTREQUEST_0_CURRENCYCODE"] = server.UrlEncode(currencyCodeType);
            nvpstr["REQCONFIRMSHIPPING"] = "0";
            nvpstr["NOSHIPPING"] = "1";
            nvpstr["PAYMENTREQUEST_0_CUSTOM"] = customField;

            if (payByCreditCard)
            {
                nvpstr["SOLUTIONTYPE"] = "Sole";
                nvpstr["LANDINGPAGE"] = "Billing";
            }

            for(int i = 0; i < items.Count; i++)
            {
                nvpstr["L_PAYMENTREQUEST_0_NAME" + i.ToString()] = items[i].name;
                nvpstr["L_PAYMENTREQUEST_0_AMT" + i.ToString()] = server.UrlEncode(items[i].amt);
                nvpstr["L_PAYMENTREQUEST_0_QTY" + i.ToString()] = server.UrlEncode(items[i].qty);
                nvpstr["L_PAYMENTREQUEST_0_ITEMCATEGORY" + i.ToString()] = itemCategory;
            }

            /*
		    string nvpstr = "&PAYMENTREQUEST_0_AMT=" + paymentAmount;
		    nvpstr += "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType;
		    nvpstr += "&RETURNURL=" + returnURL;
		    nvpstr += "&CANCELURL=" + cancelURL;
		    nvpstr += "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType;
		    nvpstr += "&REQCONFIRMSHIPPING=0";
		    nvpstr += "&NOSHIPPING=1";
		    nvpstr += "&PAYMENTREQUEST_0_CUSTOM=" + trackId;

            for(int i = 0; i < items.Count; i++)
            {
			    nvpstr += "&L_PAYMENTREQUEST_0_NAME" + i.ToString() + "=" + server.UrlEncode(items[i].name);
			    nvpstr += "&L_PAYMENTREQUEST_0_AMT" + i.ToString() + "=" + server.UrlEncode(items[i].amt);
			    nvpstr += "&L_PAYMENTREQUEST_0_QTY" + i.ToString() + "=" + server.UrlEncode(items[i].qty);
			    nvpstr += "&L_PAYMENTREQUEST_0_ITEMCATEGORY" + i.ToString() + "=Physical";
            }
            */

            NameValueCollection result = hashCall("SetExpressCheckout", nvpstr);
            string ack = result.GetValues("ACK").First();
            ack = ack.ToUpper();
		    if(ack == "SUCCESS" || ack == "SUCCESSWITHWARNING")
		    {
			    token = result.GetValues("TOKEN").First();
		    }

            return result;
        }

        public NameValueCollection GetExpressCheckoutDetails(string tokenStr)
        {
            NameValueCollection nvpstr = new NameValueCollection();
            nvpstr["TOKEN"] = tokenStr;
            NameValueCollection result = hashCall("GetExpressCheckoutDetails", nvpstr);
            return result;
        }

        public NameValueCollection ConfirmPayment(string token, string paymentType, string currencyCodeType, string payerID, string FinalPaymentAmt, List<PayPalItem> items, string customField, string serverName, string itemCategory)
        {
            HttpServerUtility server = HttpContext.Current.Server;
            NameValueCollection nvpstr = new NameValueCollection();
            nvpstr["TOKEN"] = server.UrlEncode(token);
            nvpstr["PAYERID"] = server.UrlEncode(payerID);
            nvpstr["PAYMENTREQUEST_0_AMT"] = server.UrlEncode(FinalPaymentAmt);
            nvpstr["PAYMENTREQUEST_0_PAYMENTACTION"] = server.UrlEncode(paymentType);
            nvpstr["PAYMENTREQUEST_0_CURRENCYCODE"] = server.UrlEncode(currencyCodeType);
            nvpstr["IPADDRESS"] = server.UrlEncode(serverName);
            nvpstr["PAYMENTREQUEST_0_CUSTOM"] = customField;
            for(int i = 0; i < items.Count; i++)
            {
                nvpstr["L_PAYMENTREQUEST_0_NAME" + i.ToString()] = items[i].name;
                nvpstr["L_PAYMENTREQUEST_0_AMT" + i.ToString()] = server.UrlEncode(items[i].amt);
                nvpstr["L_PAYMENTREQUEST_0_QTY" + i.ToString()] = server.UrlEncode(items[i].qty);
                nvpstr["L_PAYMENTREQUEST_0_ITEMCATEGORY" + i.ToString()] = itemCategory;
            }

            return hashCall("DoExpressCheckoutPayment", nvpstr);
        }

        public NameValueCollection hashCall(String methodName, NameValueCollection nvpStr)
        {
            HttpServerUtility server = HttpContext.Current.Server;
            WebClient webClient = new WebClient();
            nvpStr["METHOD"] = server.UrlEncode(methodName);
            nvpStr["VERSION"] = server.UrlEncode(version);
            nvpStr["PWD"] = server.UrlEncode(API_Password);
            nvpStr["USER"] = server.UrlEncode(API_UserName);
            nvpStr["SIGNATURE"] = server.UrlEncode(API_Signature);
            nvpStr["BUTTONSOURCE"] = server.UrlEncode(sBNCode);

            var response = webClient.UploadValues(API_Endpoint, "POST", nvpStr);
            string result = System.Text.Encoding.UTF8.GetString(response);
            return deformatNVP(result);
        }

        public NameValueCollection deformatNVP(string nvpStr)
        {
            HttpServerUtility server = HttpContext.Current.Server;
            NameValueCollection result = new NameValueCollection();
            //nvpStr.Substring
            string[] v = nvpStr.Split('&');
            foreach (string value in v)
            {
                int pos = value.IndexOf('='); //zero based
                string key = server.UrlDecode(value.Substring(0, pos));
                result[key] = server.UrlDecode(value.Substring(pos + 1));
            }

            return result;
        }
    }
}
