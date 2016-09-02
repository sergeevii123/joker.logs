FROM centos:centos7

MAINTAINER Maxim Gorelikov <somebody (at) some-thing.org>

COPY add-rsyslog_forwading_conf.sh start-rsyslog.sh /usr/bin/
ADD http://rpms.adiscon.com/v8-stable/rsyslog.repo /etc/yum.repos.d

RUN  yum -y update \
  && yum install -y rsyslog rsyslog-kafka \
  && yum clean all \
  && chmod +x /usr/bin/add-rsyslog_forwading_conf.sh \
  && chmod +x /usr/bin/start-rsyslog.sh

EXPOSE 514 514/udp

VOLUME [ "/var/log", "/etc/rsyslog.d" ]

# for some reason, the apk comes built with a v5
# config file. using this one for v8:
#COPY ./etc/rsyslog.conf /tmp/rsyslog.conf

CMD ["/usr/bin/start-rsyslog.sh"]