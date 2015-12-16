#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <algorithm>

using namespace std;

int main()
{
	char ss[100000];
	gets(ss);
	while (ss[0])
	{
		int length = strlen(ss);
		for (int i = 0; i < length; ++i)
			if ((ss[i] != '[') && (ss[i] != ']'))
			{
				if (ss[i] != '"')
				{
					printf("%c", ss[i]);
					continue;
				}
				bool ff = false;
				int j = i - 1;
				for (; j >= 0; --j)
					if (j != ' ')
					{
						if (j == ':')
							ff = true;
						break;
					}
				j = i + 1;
				for (; j < length; ++j)
					if (j != ' ')
					{
						if (j == ',')
							ff = true;
						break;
					}
				if (ff)
					printf("%c", ss[i]);
			}
		printf("\n");
		ss[0] = 0;
		gets(ss);
	}
}
