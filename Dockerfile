FROM alpine:3.17

RUN apk add --update --no-cache python3
RUN python3 -m ensurepip
RUN pip3 install --no-cache --upgrade ssh-tarpit