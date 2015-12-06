from scrapy.http import Request
from scrapy.exceptions import NotConfigured

class RefererMiddleware(object):

	@classmethod
	def from_crawler(cls, crawler):
		if not crawler.settings.getbool('REFERER_ENABLED'):
			raise NotConfigured
		return cls()

	def process_spider_output(self, response, result, spider):
		def set_referer(r):
			if isinstance(r, Request):
				r.headers.setdefault('Referer', response.url)
			return r
		return (set_referer(r) for r in result or ())
