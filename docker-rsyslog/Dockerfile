FROM centos:centos7

MAINTAINER Maxim Gorelikov <somebody (at) some-thing.org>

COPY add-rsyslog_forwading_conf.sh start-rsyslog.sh /usr/bin/

RUN  yum -y update \
  && yum clean all \
  && yum install -y rsyslog\
  && yum clean all \
  && chmod +x /usr/bin/add-rsyslog_forwading_conf.sh \
  && chmod +x /usr/bin/start-rsyslog.sh

EXPOSE 514 514/udp

VOLUME [ "/var/log", "/etc/rsyslog.d" ]

# for some reason, the apk comes built with a v5
# config file. using this one for v8:
#COPY ./etc/rsyslog.conf /tmp/rsyslog.conf

CMD ["/usr/bin/start-rsyslog.sh"]