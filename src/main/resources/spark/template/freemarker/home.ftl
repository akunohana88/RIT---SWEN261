<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->

   <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
      <h3> Players Online: ${playersOnline}</h3>
    <#if signedIn??>
        <h4> You are signed in : ${username} </h4>
            <form action="/game" method="GET">
            <ol>
                <#list names as n>
                    <#if currentUser.name != n.name>

                            <ul>
                                <button type="submit" name = "playerNames" value = "${n.name}">${n.name}</button>
                                <#if n.game??>
                                  (Spectate)
                                </#if>
                                <br>
                            </ul>
                            <br>
                    </#if>
                </#list>
            </ol>

            </form>
    </#if>
    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
