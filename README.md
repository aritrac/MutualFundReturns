# MutualFundReturns
Calculating mutual fund returns
Mutual Fund Returns

Historical NAV data for Indian mutual funds are provided by the site https://www.mfapi.in/. For instance NAV data of “SBI Equity Hybrid Fund” can be got in JSON format using the URL https://api.mfapi.in/mf/102885


You are required to compute the trailing returns for a Mutual fund investment month wise for different time period of investments over a time period (horizon). 

Returns are calculated using the formula.

Returns = (End Value/Start Value)^(1/Years)-1


Say a mutual fund started with a value of Rs 1000 on January 1, 2019, ended with NAV of Rs 2500 on January 1, 2026.
Then year-on-year return will be calculated like this
Returns = (2500/1000)^(1/7) – 1 = 0.14 =~ 14%
 
You are required to find the trailing returns for given period of investment and horizon. Horizon means for how many months you need to find the returns.
 
Program outline
Get the scheme number from user
Download historic NAV data from mfapi site
Get the period of investment and horizon from the user
Compute trailing returns for entire horizon month over month for the latest date available in NAV data
 
Example 1:
You are running the program on 16-Jul-20, the latest NAV you will have would be for 15-Jul-20
Input
Period of Investment = 1 
Horizon = 1
 
Output
Month wise returns

| Month  | Returns   | Calculation   |
|---|---|---|
| Aug-19  |xx%   |Start nav - 16-Aug-18 End nav - 16-Aug-19   |
| Sep-19  |xx%   |Start nav - 16-Sep-18 End nav - 16-Sep-19   |
| Oct-19  |xx%   |Start nav - 16-Oct-18 End nav - 16-Oct-19   |
| Nov-19  |xx%   |Start nav - 16-Nov-18 End nav - 16-Nov-19   |
| Dec-19  |xx%   |Start nav - 16-Dec-18 End nav - 16-Dec-19   |
| Jan-20  |xx%   |Start nav - 16-Jan-19 End nav - 16-Aug-20   |
| Feb-20  |xx%   |Start nav - 16-Fev-19 End nav - 16-Aug-20   |
| Mar-20  |xx%   |Start nav - 16-Mar-19 End nav - 16-Aug-20   |
| Apr-20  |xx%   |Start nav - 16-Apr-19 End nav - 16-Aug-20   |
| May-20  |xx%   |Start nav - 16-May-19 End nav - 16-Aug-20   |
| Jun-20  |xx%   |Start nav - 16-Jun-19 End nav - 16-Aug-20   |
| Jul-20  |xx%   |Start nav - 16-Jul-19 End nav - 16-Aug-20   |

Example 2:
Suppose
Period of investment = 3
Horizon = 5

Output
Month wise returns

| Month  | Returns   | Calculation   |
|---|---|---|
| Aug-15  |xx%   |Start nav - 16-Aug-12 End nav - 16-Aug-15   |
| Sep-15  |xx%   |Start nav - 16-Sep-12 End nav - 16-Sep-15   |
|         |      |              rows from Oct-15 to Jun-20   |
| Jul-20  |xx%   |Start nav - 16-Jul-17 End nav - 16-Jul-20   |


Note
If NAV for a particular date is not available, choose the NAV of next available date
If data is not available (if date is before launch date) return zero as the return

Tech stack
Any tech stack comfortable
App can be a pure command line based application built on Java or Spring or Python or it can be pure JavaScript app built on top of framework like Angular or react

Reference
https://www.investopedia.com/ask/answers/050415/what-good-annual-return-mutual-fund.asp
https://www.investopedia.com/articles/08/annualized-returns.asp

