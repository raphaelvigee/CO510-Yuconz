<#include 'base.ftl'>

<#macro page_body>
<div id="dashboard">

    <div id="nav">
        <div class="branding">
            <h1>Yuconz</h1>
            <h2>HR Portal</h2>
        </div>
        <div class="account">
            <div class="option">
                <div class="accountName">Brychan Bennett-Odlum</div>
                <div class="accountIcon"></div>
            </div>
        </div>
    </div>

    <div id="sidePanel">
        <div class="heading">
            My Yuconz
        </div>
        <div class="option selected">
            <a href="#">Dashboard</a>
        </div>
        <div class="option">
            <a href="#">Personal Details</a>
        </div>
        <div class="option">
            <a href="#">Annual Reviews</a>
        </div>
        <div class="option">
            <a href="#">Employment History</a>
        </div>
        <div class="option">
            <a href="/auth/logout">Logout</a>
        </div>


        <div class="heading">
            Administration
        </div>
        <div class="option">
            <a href="#">Dashboard</a>
        </div>
        <div class="option">
            <a href="#">Employee Search</a>
        </div>
        <div class="option">
            <a href="#">Employment History</a>
        </div>
    </div>

    <div id="mainContent">

        123

    </div>
</div>

</#macro>

<#macro page_head>
<link rel="stylesheet" rel="text/css" href="/assets/css/dashboard.css">
</#macro>

<@display_page/>