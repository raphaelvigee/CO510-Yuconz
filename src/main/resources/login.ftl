<#include "base.ftl">

<#macro page_body>

<div id="loginView">

    <div id="loginHeader">
        <div id="loginHeaderIcon"></div>
        <div id="loginHeaderTitle">HR Portal</div>
        <div id="loginHeaderSubtitle">Please login to continue.</div>
    </div>

    <form method="post">
        <div class="inputSection padded">
            <h3>Email Address</h3>
            <input type="text" placeholder="example@yuconz.co.uk" name="email">
        </div>
        <div class="inputSection padded">
            <h3>Password</h3>
            <input type="password" placeholder="Password" name="password">
        </div>
        <div class="inputSection padded">
            <h3>Role</h3>
            <div class="selectWrapper">
                <select name="role">
                    <option>Employee</option>
                    <option>Manager</option>
                    <option>Director</option>
                </select>
            </div>
        </div>
        <div class="inputSection">
            <input type="submit" value="Login">
        </div>
        <div class="detailSection">
            <a href="#">Forgotten password?</a>
        </div>
        <div id="loginFooter">
            <span>Confidential resource, provided by Yuconz.</span>
            <br>
            <span>Secure form sent via HTTPS.</span>
        </div>
    </form>

</div>

</#macro>

<#macro page_head>
<link rel="stylesheet" rel="text/css" href="/assets/css/login.css">
</#macro>

<@display_page/>