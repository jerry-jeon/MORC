MORC (MO Routing client)
===============================
An android client for sending post requests when it receive SMS or MMS.

How to use it?
------------------------------
1. Add routing data. *This is only what you do.
2. When a message comes, it checks to see if it satisfies the condition. Send post request if it passes.
3. It makes log including response status code.

Routing data
-----------------------
### Routing condition
| Name | Description |
| ---- |-------------|
| from | Sender's number |
| regex | Used for match message |


You can provide only one condition. If you provide both, the message must satisfy both.

### Request
| Name | Description |
| ---- |-------------|
| authorization | Request header |
| tags | Tokenized string by ',' which included in request body. ex) "A, B" -> ["A", "B"] |
| urls | Url to reqeust. It can be several. |


Issue
-----------
MMS' title crashes when it contains korean characters.
